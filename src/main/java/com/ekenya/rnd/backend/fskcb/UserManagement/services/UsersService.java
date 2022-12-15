package com.ekenya.rnd.backend.fskcb.UserManagement.services;

import com.ekenya.rnd.backend.fskcb.AdminModule.datasource.entities.UserAuditTrailEntity;
import com.ekenya.rnd.backend.fskcb.AdminModule.datasource.repositories.IUserAuditTrailRepo;
import com.ekenya.rnd.backend.fskcb.AuthModule.datasource.entities.SecQuestionOptionEntity;
import com.ekenya.rnd.backend.fskcb.AuthModule.datasource.entities.SecurityQuestionEntity;
import com.ekenya.rnd.backend.fskcb.AuthModule.datasource.entities.SecurityQuestionType;
import com.ekenya.rnd.backend.fskcb.AuthModule.datasource.repositories.ISecurityQuestionsRepo;
import com.ekenya.rnd.backend.fskcb.AuthModule.services.ISmsService;
import com.ekenya.rnd.backend.fskcb.CrmAdapters.services.ICRMService;
import com.ekenya.rnd.backend.fskcb.UserManagement.datasource.entities.*;
import com.ekenya.rnd.backend.fskcb.UserManagement.datasource.repositories.ProfilesAndUsersRepository;
import com.ekenya.rnd.backend.fskcb.UserManagement.datasource.repositories.RoleRepository;
import com.ekenya.rnd.backend.fskcb.UserManagement.datasource.repositories.UserProfilesRepository;
import com.ekenya.rnd.backend.fskcb.UserManagement.datasource.repositories.UserRepository;
import com.ekenya.rnd.backend.fskcb.UserManagement.helper.ExcelHelper;
import com.ekenya.rnd.backend.fskcb.UserManagement.models.ExcelImportError;
import com.ekenya.rnd.backend.fskcb.UserManagement.models.UsersExcelImportResult;
import com.ekenya.rnd.backend.fskcb.UserManagement.models.reps.*;
import com.ekenya.rnd.backend.fskcb.UserManagement.payload.AddAdminUserRequest;
import com.ekenya.rnd.backend.fskcb.exception.UserNotFoundException;
import com.ekenya.rnd.backend.utils.Status;
import com.ekenya.rnd.backend.utils.Utility;
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

    @Autowired
    ISecurityQuestionsRepo securityQuestionsRepo;
    private ObjectMapper mObjectMapper = new ObjectMapper();

    public boolean updateResetPasswordToken(String token, String email) throws UserNotFoundException {
        UserAccountEntity userAccount = userRepository.findByEmail(email).get();
        if (userAccount != null) {
            userAccount.setResetPasswordToken(token);
            userRepository.save(userAccount);

            return true;
        } else {
            throw new UserNotFoundException("User not found with email " + email);
        }

    }

    public UserAccountEntity getByResetPasswordToken(String token) {
        return userRepository.findByResetPasswordToken(token);
    }

    public boolean attemptUpdatePassword(UpdatePasswordRequest model) {

        try {
            UserAccountEntity account = userRepository.findById(model.getUserId()).get();
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
    public UserAccountEntity findById(Long id) {
        return userRepository.findById(id).orElse(null);//.orElseThrow(() -> new ResourceNotFoundException("UseApp","id", + id));
    }

    @Override
    public UserAccountEntity findByStaffNo(String staffNo) {
        return userRepository.findByStaffNo(staffNo).orElse(null);//
    }

    @Override
    public boolean attemptCreateUser(AddAdminUserRequest model, AccountType type, boolean verified) {


        try{

            if(!userRepository.findByStaffNo(model.getStaffNo()).isPresent()){

                //
                String password = Utility.generatePassword();
                //
                UserAccountEntity account = new UserAccountEntity();
                account.setPhoneNumber(model.getPhoneNo());
                account.setAccountType(type);
                account.setFullName(model.getFullName());
                account.setEmail(model.getEmail());
                account.setStaffNo(model.getStaffNo());
                account.setIsVerified(verified);
                //
                account.setPassword(passwordEncoder.encode(password));
                //userRepository.save(account);//save user to db
                //

                if(type == AccountType.ADMIN) {
                    //CAN ACCESS PORTAL
                    UserRoleEntity userRole = roleRepository.findByName(SystemRoles.ADMIN).get();//get role from db
                    account.setRoles(Collections.singleton(userRole));//set role to user
                    userRepository.save(account);//save user to db

                    //
                    if(smsService.sendPasswordEmail(account.getEmail(),account.getFullName(),password)){
                        //
                        //return true;
                    }
                }else{
                    //DSR Account..
                    UserRoleEntity userRole = roleRepository.findByName(SystemRoles.DSR).get();//get role from db
                    account.setRoles(Collections.singleton(userRole));//set role to user
                    userRepository.save(account);//save user to db
                }
                //
                return true;
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
            for (UserAccountEntity account: results.getAccounts()) {
                //
                if(!userRepository.findByStaffNo(account.getStaffNo()).isPresent()){
                    //
                    String password = Utility.generatePassword();
                    //
                    account.setPassword(passwordEncoder.encode(password));
                    //add user to db and assign default  role
                    Optional<UserRoleEntity> role = roleRepository.findByName(SystemRoles.ADMIN);
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
            for(UserAccountEntity account : userRepository.findAllByAccountTypeAndStatus(AccountType.ADMIN,Status.ACTIVE)){
                ObjectNode node = mObjectMapper.createObjectNode();

                node.put("id",account.getId());
                node.put("name",account.getFullName());
                node.put("staffNo",account.getStaffNo());
                node.put("email",account.getEmail());
                node.put("phoneNo",account.getPhoneNumber());
                node.put("verified",account.isVerified());
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
//                ArrayNode profiles = mObjectMapper.createArrayNode();
//
//                for (ProfileAndUserEntity userAndProfile:
//                        profilesAndUsersRepository.findAllByUserId(account.getId())) {
//                    UserProfileEntity userProfile = userProfilesRepository.findById(userAndProfile.getProfileId()).orElse(null);
//                    //
//                    if(userAndProfile != null) {
//                        ObjectNode node1 = mObjectMapper.createObjectNode();
//                        node1.put("name",userProfile.getName());
//                        node1.put("code",userProfile.getCode());
//                        profiles.add(node1);
//                    }
//                }
//                //
//                node.putPOJO("profiles",profiles);
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
                    UserAccountEntity account = new UserAccountEntity();
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
                    Optional<UserRoleEntity> role = roleRepository.findByName(SystemRoles.ADMIN);
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
    public ObjectNode loadUserDetails(AdminUserDetailsRequest model) {

        try{

            Optional<UserAccountEntity> account = null;
            if(model.getUserId() != null ) {
                userRepository.findById(model.getUserId());
            }else if(model.getStaffNo() != null ){
                userRepository.findByStaffNo(model.getStaffNo());
            }
            //
            if(account == null || account.isEmpty()){
                return  null;
            }
            //
            ObjectNode node = mObjectMapper.createObjectNode();
            node.put("id",account.get().getId());
            node.put("name",account.get().getFullName());
            node.put("staffNo",account.get().getStaffNo());
            node.put("email",account.get().getEmail());
            node.put("phoneNo",account.get().getPhoneNumber());
            node.put("verified",account.get().isVerified());
            node.put("type",account.get().getAccountType().toString());
            try {
                if(account.get().getLastLogin() != null) {
                    node.put("lastLogin", dateFormat.format(account.get().getLastLogin()));
                }else{
                    node.put("lastLogin", "");
                }
            }catch (Exception ex){
                log.error(ex.getMessage(),ex);
                node.put("lastLogin", "");
            }
            node.put("lastLocation",account.get().getLastCoords());

            //Roles
            ArrayNode roles = mObjectMapper.createArrayNode();
            for (UserRoleEntity role: account.get().getRoles()) {
                ObjectNode j = mObjectMapper.createObjectNode();
                j.put("name",role.getName());
                j.put("desc",role.getInfo());
                roles.add(j);
            }
            node.putPOJO("roles", roles);

            //Profiles
            ArrayNode profiles = mObjectMapper.createArrayNode();
            for (ProfileAndUserEntity userProfiles:
                 profilesAndUsersRepository.findAllByUserId(account.get().getId())) {
                UserProfileEntity profile = userProfilesRepository.findById(userProfiles.getProfileId()).get();

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
            Optional<UserAccountEntity> account = userRepository.findByStaffNo(model.getStaffNo());
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
            UserAccountEntity account = userRepository.findByStaffNo(model.getStaffNo()).get();
            //
            for (long pid: model.getProfiles()) {
                //
                UserProfileEntity profile = userProfilesRepository.findById(pid).get();
                //
                if(!profilesAndUsersRepository.findAllByUserIdAndProfileIdAndStatus(account.getId(),profile.getId(),Status.ACTIVE).isPresent()){
                    //
                    ProfileAndUserEntity userProfile = new ProfileAndUserEntity();
                    userProfile.setProfileId(profile.getId());
                    userProfile.setUserId(account.getId());
                    //
                    profilesAndUsersRepository.save(userProfile);
                }else{
                    //Already exists ..
                }
            }
//            UserRole userRole = roleRepository.findById(roleId).orElse(null);
//            UserProfileEntity userProfile = userProfilesRepository.findById(privilegeId).orElse(null);
//            Set<UserProfileEntity> roleUserProfiles = (Set<UserProfileEntity>) userRole.getUserProfiles();
//            roleUserProfiles.add(userProfile);
//            userRole.setUserProfiles(roleUserProfiles);
//            roleRepository.save(userRole);

            return true;
        }catch (Exception e){
            log.error(e.getMessage(),e);
        }
        return false;
    }

    @Override
    public boolean updateUserProfiles(UpdateUserProfilesRequest model) {
        try{
            //
            UserAccountEntity account = userRepository.findByStaffNo(model.getStaffNo()).get();
            //Selected
            for (long pid: model.getProfiles()) {
                //
                UserProfileEntity profile = userProfilesRepository.findById(pid).get();
                //
                Optional<ProfileAndUserEntity> profileUser =
                        profilesAndUsersRepository.findAllByUserIdAndProfileIdAndStatus(account.getId(),profile.getId(),Status.ACTIVE);
                //New Adding...
                if(!profileUser.isPresent()){
                    //
                    ProfileAndUserEntity userProfile = new ProfileAndUserEntity();
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
            for (ProfileAndUserEntity e: profilesAndUsersRepository.findAllByUserId(account.getId())) {
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

//        Set<UserProfileEntity> roleUserProfiles = (Set<UserProfileEntity>) userRole.getUserProfiles();
//        roleUserProfiles.removeIf(x -> x.getId().equals(privilegeId));
//        userRole.setUserProfiles(roleUserProfiles);
//        roleRepository.save(userRole);
            return true;
        }catch (Exception e){
            log.error(e.getMessage(),e);
        }
        return false;
    }

    @Override
    public boolean attemptBlockUser(Long userId) {

        try{

            UserAccountEntity account = userRepository.findById(userId).get();
            //
            account.setBlocked(true);
            account.setRemLoginAttempts(0);
            account.setLastModified(Calendar.getInstance().getTime());
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

            UserAccountEntity account = userRepository.findById(userId).get();
            //
            account.setBlocked(false);
            account.setRemLoginAttempts(3);
            account.setLastModified(Calendar.getInstance().getTime());
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

            UserAccountEntity account = userRepository.findById(userId).get();
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

    @Override
    public boolean attemptCreateSecurityQuestion(AddSecurityQnRequest model) {

        try{

            if(!securityQuestionsRepo.findByTitle(model.getTitle()).isPresent()){

                SecurityQuestionEntity qn = new SecurityQuestionEntity();
                qn.setStatus(Status.ACTIVE);
                qn.setTitle(model.getTitle());
                if(model.getType().equalsIgnoreCase(SecurityQuestionType.YES_NO.toString())) {
                    qn.setType(SecurityQuestionType.YES_NO);
                }else if(model.getType().equalsIgnoreCase(SecurityQuestionType.SELECT_OPTIONS.toString())) {
                    //
                    qn.setType(SecurityQuestionType.SELECT_OPTIONS);
                    //
                    if(model.getChoices()!=null && !model.getChoices().isEmpty()){
                        List<SecQuestionOptionEntity> optionEntities = new ArrayList<>();
                        for (String e: model.getChoices()) {
                            SecQuestionOptionEntity option = new SecQuestionOptionEntity();
                            option.setTitle(e);
                            optionEntities.add(option);
                        }
                        qn.setOptions(optionEntities);
                    }
                }else if(model.getType().equalsIgnoreCase(SecurityQuestionType.MULTI_LINE.toString())) {
                    qn.setType(SecurityQuestionType.MULTI_LINE);
                }else if(model.getType().equalsIgnoreCase(SecurityQuestionType.NUMERICAL.toString())) {
                    qn.setType(SecurityQuestionType.NUMERICAL);
                }else{
                    qn.setType(SecurityQuestionType.ONE_LINE);
                }

                //
                securityQuestionsRepo.save(qn);
            }
            return true;
        }catch (Exception e){
            log.error(e.getMessage(),e);
        }
        return false;
    }

    @Override
    public List<ObjectNode> loadAllSecurityQuestions() {

        try{
            List<ObjectNode> list = new ArrayList<>();
            for(SecurityQuestionEntity qn : securityQuestionsRepo.findAll()){
                ObjectNode node = mObjectMapper.createObjectNode();

                node.put("id",qn.getId());
                node.put("title",qn.getTitle());
                node.put("type",qn.getType().toString());

                if(!qn.getOptions().isEmpty()) {
                    ArrayNode options = mObjectMapper.createArrayNode();
                    for (SecQuestionOptionEntity opt:
                         qn.getOptions()) {

                        ObjectNode option = mObjectMapper.createObjectNode();
                        option.put("title",opt.getTitle());
                        option.put("id",opt.getId());
                        options.add(option);
                    }
                    node.putPOJO("options", options);
                }
                try {
                    if(qn.getDateCreated() != null) {
                        node.put("dateCreated", dateFormat.format(qn.getDateCreated()));
                    }else{
                        node.put("dateCreated", "");
                    }
                }catch (Exception ex){
                    log.error(ex.getMessage(),ex);
                    node.put("dateCreated", "");
                }
                //
                list.add(node);
            }
            return list;
        }catch (Exception e){
            log.error(e.getMessage(),e);
        }
        return null;
    }


}
