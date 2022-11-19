package com.ekenya.rnd.backend.fskcb.UserManagement.services;

import com.ekenya.rnd.backend.fskcb.AdminModule.datasource.entities.UserAuditTrailEntity;
import com.ekenya.rnd.backend.fskcb.AdminModule.datasource.repositories.IUserAuditTrailRepo;
import com.ekenya.rnd.backend.fskcb.AuthModule.services.ISmsService;
import com.ekenya.rnd.backend.fskcb.CrmAdapter.ICRMService;
import com.ekenya.rnd.backend.fskcb.UserManagement.datasource.entities.*;
import com.ekenya.rnd.backend.fskcb.UserManagement.datasource.repositories.ProfilesAndUsersRepository;
import com.ekenya.rnd.backend.fskcb.UserManagement.datasource.repositories.RoleRepository;
import com.ekenya.rnd.backend.fskcb.UserManagement.datasource.repositories.UserProfilesRepository;
import com.ekenya.rnd.backend.fskcb.UserManagement.datasource.repositories.UserRepository;
import com.ekenya.rnd.backend.fskcb.UserManagement.helper.ExcelHelper;
import com.ekenya.rnd.backend.fskcb.UserManagement.models.ExcelImportError;
import com.ekenya.rnd.backend.fskcb.UserManagement.models.UsersExcelImportResult;
import com.ekenya.rnd.backend.fskcb.UserManagement.models.reps.AssignUserProfileRequest;
import com.ekenya.rnd.backend.fskcb.UserManagement.models.reps.ResetUserPasswordRequest;
import com.ekenya.rnd.backend.fskcb.UserManagement.models.reps.UpdatePasswordRequest;
import com.ekenya.rnd.backend.fskcb.UserManagement.models.reps.UpdateUserProfileRequest;
import com.ekenya.rnd.backend.fskcb.UserManagement.payload.AddUserRequest;
import com.ekenya.rnd.backend.fskcb.exception.UserNotFoundException;
import com.ekenya.rnd.backend.utils.Status;
import com.ekenya.rnd.backend.utils.Utility;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.text.DateFormat;
import java.util.*;

@Service
@Slf4j
@Transactional
public class UsersService implements IUsersService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private ISmsService smsService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private DateFormat dateFormat;

    @Autowired
    ProfilesAndUsersRepository profilesAndUsersRepository;
    @Autowired
    UserProfilesRepository userProfilesRepository;
    @Autowired
    ICRMService crmService;
    @Autowired
    IUserAuditTrailRepo userAuditTrailRepository;

    private ObjectMapper mObjectMapper = new ObjectMapper();

    public boolean updateResetPasswordToken(String token, String email) throws UserNotFoundException {
        UserAccount userAccount = userRepository.findByEmail(email).get();
        if (userAccount != null) {
            userAccount.setResetPasswordToken(token);
            userRepository.save(userAccount);

            return true;
        } else {
            throw new UserNotFoundException("User not found with email " + email);
        }

    }

    public UserAccount getByResetPasswordToken(String token) {
        return userRepository.findByResetPasswordToken(token);
    }

    public boolean attemptUpdatePassword(UpdatePasswordRequest model) {

        try {
            UserAccount account = userRepository.findById(model.getUserId()).get();
            //
            //BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String encodedPassword = passwordEncoder.encode(model.getNewPassword());

            account.setPassword(encodedPassword);

            account.setResetPasswordToken(null);
            userRepository.save(account);

            return true;
        }catch (Exception ex){
            log.error(ex.getMessage(),ex);
        }

        return false;
    }
    public UserAccount findById(Long id) {
        return userRepository.findById(id).orElse(null);//.orElseThrow(() -> new ResourceNotFoundException("UseApp","id", + id));
    }

    @Override
    public UserAccount findByStaffNo(String staffNo) {
        return userRepository.findByStaffNo(staffNo).orElse(null);//
    }

    @Override
    public boolean attemptCreateUser(AddUserRequest model) {


        try{

            if(!userRepository.findByStaffNo(model.getStaffNo()).isPresent()){

                //
                String password = Utility.generatePassword();
                //
                UserAccount account = new UserAccount();
                account.setPhoneNumber(model.getPhoneNo());
                account.setAccountType(AccountType.ADMIN);
                account.setFullName(model.getFullName());
                account.setEmail(model.getEmail());
                //
                account.setPassword(passwordEncoder.encode(password));
                userRepository.save(account);//save user to db
                //

                //CAN ACCESS PORTAL
                UserRole userRole = roleRepository.findByName(SystemRoles.ADMIN).get();//get role from db
                account.setRoles(Collections.singleton(userRole));//set role to user
                userRepository.save(account);//save user to db

                //
                if(smsService.sendPasswordEmail(account.getEmail(),account.getFullName(),password)){
                    //
                    return true;
                }
            }

        }catch (Exception ex){
            log.error(ex.getMessage(),ex);
        }

        return false;
    }

    @Override
    public ObjectNode attemptImportUsers(MultipartFile importFile) {


        try{

            UsersExcelImportResult results = ExcelHelper.excelToUserAccounts(importFile.getInputStream());

            int imported = 0;
            for (UserAccount account: results.getAccounts()) {
                //
                if(!userRepository.findByStaffNo(account.getStaffNo()).isPresent()){
                    //
                    String password = Utility.generatePassword();
                    //
                    account.setPassword(passwordEncoder.encode(password));
                    //add user to db and assign default  role
                    Optional<UserRole> role = roleRepository.findByName(SystemRoles.ADMIN);
                    //set role
                    account.setRoles(new HashSet<>(Arrays.asList(role.get())));
                    //
                    userRepository.save(account);
                    //
                    if(smsService.sendPasswordEmail(account.getEmail(),account.getFullName(),password)){
                        //
                    }else{
                        results.getErrors().add(new ExcelImportError("Send password for "+account.getEmail()+" failed."));
                    }
                    imported ++;
                }else{
                    results.getErrors().add(new ExcelImportError(0,0,"An account with Staff No '"+account.getStaffNo()+"' already exists"));
                }
            }
            //
            if(!results.getErrors().isEmpty()){
                //
                ObjectNode node = mObjectMapper.createObjectNode();
                node.put("imported",imported);
                node.putPOJO("import-errors",mObjectMapper.convertValue(results.getErrors(),ArrayNode.class));
                //
                return node;
            }else{
                //
                return mObjectMapper.createObjectNode();
            }
        }catch (Exception ex){
            log.error(ex.getMessage(),ex);
        }
        return null;
    }

    @Override
    public List<ObjectNode> loadAllUsers() {

        try{
            List<ObjectNode> list = new ArrayList<>();
            for(UserAccount account : userRepository.findAll()){
                ObjectNode node = mObjectMapper.createObjectNode();

                node.put("id",account.getId());
                node.put("name",account.getFullName());
                node.put("staffNo",account.getStaffNo());
                node.put("email",account.getEmail());
                node.put("phoneNo",account.getPhoneNumber());
                node.put("verified",account.getIsVerified());
                node.put("type",account.getAccountType().toString());
                try {
                    if(account.getLastLogin() != null) {
                        node.put("lastLogin", dateFormat.format(account.getLastLogin()));
                    }else{
                        node.put("lastLogin", "");
                    }
                }catch (Exception ex){
                    log.error(ex.getMessage(),ex);
                    node.put("lastLogin", "");
                }
                node.put("lastLocation",account.getLastCoords());
                //
                list.add(node);
            }
            return list;
        }catch (Exception e){
            log.error(e.getMessage(),e);
        }

        return null;
    }

    @Override
    public boolean syncUsersWithCRM() {

        try{
            //Pull add user accounts
            JsonArray crmUsers = crmService.fetchStaffAccounts();

            //Create them
            for (Iterator<JsonElement> it = crmUsers.iterator(); it.hasNext(); ) {
                JsonObject crmUser = (JsonObject) it.next();

                String staffNo = crmUser.get("staff-no").getAsString();

                if(!userRepository.findByStaffNo(staffNo).isPresent()){
                    //
                    UserAccount account = new UserAccount();
                    account.setStaffNo(staffNo);
                    account.setPhoneNumber(crmUser.get("phone").getAsString());
                    account.setAccountType(AccountType.ADMIN);
                    account.setFullName(crmUser.get("name").getAsString());
                    account.setEmail(crmUser.get("email").getAsString());
                    //
                    String password = Utility.generatePassword();
                    //
                    account.setPassword(passwordEncoder.encode(password));
                    //add user to db and assign default  role
                    Optional<UserRole> role = roleRepository.findByName(SystemRoles.ADMIN);
                    //set role
                    account.setRoles(new HashSet<>(Arrays.asList(role.get())));
                    //
                    userRepository.save(account);
                    //
                    if(smsService.sendPasswordEmail(account.getEmail(),account.getFullName(),password)){
                        //
                    }
                }

            }
            return true;
        }catch (Exception e){
            log.error(e.getMessage(),e);
        }
        return false;
    }

    @Override
    public ObjectNode loadUserDetails(long userId) {

        try{

            UserAccount account = userRepository.findById(userId).get();

            ObjectNode node = mObjectMapper.createObjectNode();
            node.put("id",account.getId());
            node.put("name",account.getFullName());
            node.put("staffNo",account.getStaffNo());
            node.put("email",account.getEmail());
            node.put("phoneNo",account.getPhoneNumber());
            node.put("verified",account.getIsVerified());
            node.put("type",account.getAccountType().toString());
            try {
                if(account.getLastLogin() != null) {
                    node.put("lastLogin", dateFormat.format(account.getLastLogin()));
                }else{
                    node.put("lastLogin", "");
                }
            }catch (Exception ex){
                log.error(ex.getMessage(),ex);
                node.put("lastLogin", "");
            }
            node.put("lastLocation",account.getLastCoords());

            //Roles
            ArrayNode roles = mObjectMapper.createArrayNode();
            for (UserRole role: account.getRoles()) {
                ObjectNode j = mObjectMapper.createObjectNode();
                j.put("name",role.getName());
                j.put("desc",role.getInfo());
                roles.add(j);
            }
            node.putPOJO("roles", roles);

            //Profiles
            ArrayNode profiles = mObjectMapper.createArrayNode();
            for (ProfileUserEntity userProfiles:
                 profilesAndUsersRepository.findAllByUserId(account.getId())) {
                UserProfile profile = userProfilesRepository.findById(userProfiles.getProfileId()).get();

                ObjectNode prof = mObjectMapper.createObjectNode();
                prof.put("name",profile.getName());
                prof.put("code",profile.getCode());
                prof.put("desc",profile.getInfo());

                profiles.add(prof);
            }
            //
            node.putPOJO("profiles", profiles);
            //
            return node;
        }catch (Exception e){
            log.error(e.getMessage(),e);
        }
        return null;
    }

    @Override
    public boolean attemptResetPassword(ResetUserPasswordRequest model) {

        try{
            //
            Optional<UserAccount> account = userRepository.findByStaffNo(model.getStaffNo());
            if(account.isPresent()){
                //
                String password = Utility.generatePassword();

                account.get().setPassword(passwordEncoder.encode(password));

                userRepository.save(account.get());

                if(smsService.sendPasswordEmail(account.get().getEmail(),account.get().getFullName(),password)){
                    //
                    return true;
                }
            }
        }catch (Exception e){
            log.error(e.getMessage(),e);
        }
        return false;
    }

    @Override
    public boolean assignUserToProfiles(AssignUserProfileRequest model) {

        try{
            //
            UserAccount account = userRepository.findByStaffNo(model.getStaffNo()).get();
            //
            for (long pid: model.getProfiles()) {
                //
                UserProfile profile = userProfilesRepository.findById(pid).get();
                //
                if(!profilesAndUsersRepository.findAllByUserIdAndProfileIdAndStatus(account.getId(),profile.getId(),Status.ACTIVE).isPresent()){
                    //
                    ProfileUserEntity userProfile = new ProfileUserEntity();
                    userProfile.setProfileId(profile.getId());
                    userProfile.setUserId(account.getId());
                    //
                    profilesAndUsersRepository.save(userProfile);
                }else{
                    //Already exists ..
                }
            }
            return true;
        }catch (Exception e){
            log.error(e.getMessage(),e);
        }
        return false;
    }

    @Override
    public boolean updateUserProfiles(UpdateUserProfileRequest model) {
        try{
            //
            UserAccount account = userRepository.findByStaffNo(model.getStaffNo()).get();
            //Selected
            for (long pid: model.getProfiles()) {
                //
                UserProfile profile = userProfilesRepository.findById(pid).get();
                //
                Optional<ProfileUserEntity> profileUser =
                        profilesAndUsersRepository.findAllByUserIdAndProfileIdAndStatus(account.getId(),profile.getId(),Status.ACTIVE);
                //New Adding...
                if(!profileUser.isPresent()){
                    //
                    ProfileUserEntity userProfile = new ProfileUserEntity();
                    userProfile.setProfileId(profile.getId());
                    userProfile.setUserId(account.getId());
                    //
                    profilesAndUsersRepository.save(userProfile);
                }else{
                    //Removed
                    profileUser.get().setStatus(Status.INACTIVE);
                    //
                    profilesAndUsersRepository.save(profileUser.get());
                }
            }

            //Check those note included in selection and remove
            for (ProfileUserEntity e: profilesAndUsersRepository.findAllByUserId(account.getId())) {
                //
                boolean found = false;
                for (long id: model.getProfiles()) {
                    if(id == e.getProfileId()){
                        found = true;
                    }
                }
                //Remove..
                if(!found){
                    //
                    e.setStatus(Status.INACTIVE);
                    //
                    profilesAndUsersRepository.save(e);
                }
            }

            return true;
        }catch (Exception e){
            log.error(e.getMessage(),e);
        }
        return false;
    }

    @Override
    public boolean attemptBlockUser(Long userId) {

        try{

            UserAccount account = userRepository.findById(userId).get();
            //
            account.setBlocked(true);
            userRepository.save(account);
            //
            return false;
        }catch (Exception e){
            log.error(e.getMessage(),e);
        }
        return false;
    }

    @Override
    public boolean attemptUnblockUser(Long userId) {

        try{

            UserAccount account = userRepository.findById(userId).get();
            //
            account.setBlocked(false);
            userRepository.save(account);
            //
            return false;
        }catch (Exception e){
            log.error(e.getMessage(),e);
        }
        return false;
    }

    @Override
    public ArrayNode loadUserAuditTrail(Long userId) {
        try{

            UserAccount account = userRepository.findById(userId).get();
            //

            ArrayNode list = mObjectMapper.createArrayNode();

            for (UserAuditTrailEntity entry: userAuditTrailRepository.findByUserId(account.getId())) {

                ObjectNode node = mObjectMapper.createObjectNode();
                node.put("action",entry.getAction().toString());
                node.put("desc", entry.getDetails());
                node.put("date", dateFormat.format(entry.getDateCreated()));

                list.add(node);
            }
            //
            return list;
        }catch (Exception e){
            log.error(e.getMessage(),e);
        }

        return null;
    }


}
