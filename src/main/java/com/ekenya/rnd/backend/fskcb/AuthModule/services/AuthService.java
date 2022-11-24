package com.ekenya.rnd.backend.fskcb.AuthModule.services;

import com.ekenya.rnd.backend.fskcb.AuthModule.datasource.entities.*;
import com.ekenya.rnd.backend.fskcb.AuthModule.datasource.repositories.ISecurityAuthCodesRepository;
import com.ekenya.rnd.backend.fskcb.AuthModule.datasource.repositories.ISecurityQuestionAnswersRepo;
import com.ekenya.rnd.backend.fskcb.AuthModule.datasource.repositories.ISecurityQuestionsRepo;
import com.ekenya.rnd.backend.fskcb.AuthModule.models.reqs.*;
import com.ekenya.rnd.backend.fskcb.AuthModule.models.resp.AccountLookupState;
import com.ekenya.rnd.backend.fskcb.AuthModule.models.resp.LoginResponse;
import com.ekenya.rnd.backend.fskcb.DSRModule.datasource.entities.DSRAccountEntity;
import com.ekenya.rnd.backend.fskcb.DSRModule.datasource.repositories.IDSRAccountsRepository;
import com.ekenya.rnd.backend.fskcb.UserManagement.datasource.entities.SystemRoles;
import com.ekenya.rnd.backend.fskcb.UserManagement.datasource.entities.UserAccountEntity;
import com.ekenya.rnd.backend.fskcb.UserManagement.datasource.repositories.UserProfilesRepository;
import com.ekenya.rnd.backend.fskcb.UserManagement.datasource.repositories.RoleRepository;
import com.ekenya.rnd.backend.fskcb.UserManagement.datasource.repositories.UserRepository;
import com.ekenya.rnd.backend.fskcb.UserManagement.payload.AddUserRequest;
import com.ekenya.rnd.backend.fskcb.UserManagement.security.JwtTokenProvider;
import com.ekenya.rnd.backend.fskcb.UserManagement.services.IUsersService;
import com.ekenya.rnd.backend.utils.Status;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class AuthService implements IAuthService{
    @Autowired
    ObjectMapper mObjectMapper;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserProfilesRepository userProfilesRepository;
    @Autowired
    IDSRAccountsRepository dsrAccountsRepository;
    @Autowired
    ISecurityAuthCodesRepository securityAuthCodesRepository;
    @Autowired
    private JwtTokenProvider tokenProvider;
    @Autowired
    ISmsService smsService;

    @Autowired
    IUsersService usersService;
    @Autowired
    ISecurityQuestionAnswersRepo securityQuestionAnswersRepo;
    @Autowired
    ISecurityQuestionsRepo securityQuestionsRepo;

    private java.util.logging.Logger mLogger = Logger.getLogger(getClass().getName());
    @Autowired
    FileHandler mLogFileHandler;
    @Override
    public LoginResponse attemptLogin(LoginRequest model) {

        try{

            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(model.getStaffNo(),
                    model.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            //get token from token provider
            String token= tokenProvider.generateToken(authentication);

            //get user details from authentication
            UserDetails userDetails=(UserDetails)authentication.getPrincipal();
            String username=userDetails.getUsername();

            //Update Login info ..
            UserAccountEntity account = userRepository.findByStaffNo(username).get();
            account.setLastLogin(Calendar.getInstance().getTime());
            if(model.getLocation() != null) {
                account.setLastCoords(model.getLocation().toString());
            }
            userRepository.save(account);

            //get user roles
            List<String> roles=userDetails.getAuthorities().stream().map(item->item.getAuthority()).collect(Collectors.toList());


            //Response
            LoginResponse response = new LoginResponse();
            response.setSuccess(true);
            response.setToken(token);
            response.setExpires(Calendar.getInstance().getTime());
            response.setProfiles(roles);
            response.setType("Bearer");
            //
            return response;
        }catch (AuthenticationException ex){
            //
            Optional<UserAccountEntity> optionalUserAccount = userRepository.findByStaffNo(model.getStaffNo());
            //Update ..
            if(optionalUserAccount.isPresent()){
                UserAccountEntity userAccount = optionalUserAccount.get();
                userAccount.setRemLoginAttempts(userAccount.getRemLoginAttempts() - 1);
                //
                if(userAccount.getRemLoginAttempts() <= 0){
                    userAccount.setBlocked(true);
                    userAccount.setDateBlocked(Calendar.getInstance().getTime());
                }
                //
                userRepository.save(userAccount);
                //
                LoginResponse response = new LoginResponse();
                response.setSuccess(false);
                response.setRemAttempts(userAccount.getRemLoginAttempts());
                //
                return response;
            }

            //
        }catch (Exception ex){
            //
            mLogger.log(Level.SEVERE,ex.getMessage(),ex);
        }

        return null;
    }

    @Override
    @Secured(SystemRoles.USER)
    public boolean attemptChangePIN(ChangePINRequest model) {

        try {
            //User should be authenticated,,
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
//            Object principal = SecurityContextHolder. getContext(). getAuthentication(). getPrincipal();
//            if (principal instanceof UserDetails) {
//                username = ((UserDetails)principal). getUsername();
//            } else {
//                username = principal. toString();
//            }
            //
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,model.getCurrentPIN()));

            //Update Login info ..
            UserAccountEntity account = userRepository.findByStaffNo(username).get();

            account.setPassword(passwordEncoder.encode(model.getNewPIN()));
            userRepository.save(account);//save user to db

            return true;
        }catch (Exception ex){
            mLogger.log(Level.SEVERE,"Change pin/password attempt failed.", ex);
        }
        return false;
    }

    @Override
    public AccountLookupState accountExists(LookupRequest model) {

        try {
            if(userRepository.findByStaffNoAndPhoneNumber(model.getStaffNo(), model.getPhoneNo()).isPresent()){
                //Account exists
                return AccountLookupState.ACTIVE;
            }else if(dsrAccountsRepository.findByStaffNoAndPhoneNo(model.getStaffNo(), model.getPhoneNo()).isPresent()){
                //Not Active
                return AccountLookupState.NOT_ACTIVATED;
            }
            //Not found
            return AccountLookupState.NOT_FOUND;
        }catch (Exception ex){
            mLogger.log(Level.SEVERE,ex.getMessage(),ex);
        }

        return null;
    }

    @Override
    public String sendVerificationCode(SendVerificationCodeRequest model) {
        //
        try{

            String code = smsService.sendSecurityCode(model.getStaffNo(),AuthCodeType.DEVICE_VERIFICATION);
            //
            if(code != null){
                //
                return code;
            }
        }catch (Exception ex){
            mLogger.log(Level.SEVERE,ex.getMessage(),ex);
        }

        return null;
    }

    @Override
    public boolean validateVerificationCode(ValidateVerificationCodeRequest model) {
        //

        try{
            //
            Optional<DSRAccountEntity> optionalDSRAccount =
                    dsrAccountsRepository.findByStaffNoAndPhoneNo(model.getStaffNo(), model.getPhoneNo());

            if(optionalDSRAccount.isPresent()) {

                DSRAccountEntity dsrAccount = optionalDSRAccount.get();

                Optional<SecurityAuthCodeEntity> code = securityAuthCodesRepository.findAllByCode(model.getCode());
                //
                mLogger.log(Level.INFO,"Validating token "+code);
                //Check if code exist...
                if (code.isPresent() && code.get().getUserId() == dsrAccount.getId() && !code.get().getExpired()) {
                    //
                    LocalDateTime tokenIssued = LocalDateTime.ofInstant(code.get().getDateIssued().toInstant(),
                            ZoneId.systemDefault());
                    //
                    LocalDateTime tokenExpiryTime = tokenIssued.plusMinutes(code.get().getExpiresInMinutes());
                    //
                    System.out.println("Token found'\n Issued =>  "+tokenIssued+", \nExpires => "+tokenExpiryTime+", \nNow => "+LocalDateTime.now());
                    mLogger.log(Level.INFO,"Token found'\n Issued =>  "+tokenIssued+", \nExpires => "+tokenExpiryTime+", \nNow => "+LocalDateTime.now());

                    //
                    if (tokenExpiryTime.isAfter(LocalDateTime.now())) {

                        //
                        code.get().setExpired(true);
                        securityAuthCodesRepository.save(code.get());
                        //
                        dsrAccount.setPhoneNoVerified(true);
                        dsrAccountsRepository.save(dsrAccount);

                        //
                        if(!userRepository.findByStaffNo(model.getStaffNo()).isPresent()) {

                            //Create Login Account..
                            AddUserRequest addUserRequest = new AddUserRequest();
                            addUserRequest.setEmail(dsrAccount.getEmail());
                            addUserRequest.setFullName(dsrAccount.getFullName());
                            addUserRequest.setPhoneNo(dsrAccount.getPhoneNo());
                            addUserRequest.setStaffNo(dsrAccount.getStaffNo());
                            //
                            if (usersService.attemptCreateUser(addUserRequest, true)) {
                                //All is well,
                                return true;
                            } else {
                                mLogger.log(Level.SEVERE,"Create User Account Failed");
                            }
                        }else{
                            //All is well,
                            return true;
                        }
                    } else {
                        //
                        mLogger.log(Level.SEVERE,"Device verification code is expired or user with staffNo exists ..");
                    }
                }
            }
        }catch (Exception ex){
            mLogger.log(Level.SEVERE,ex.getMessage(),ex);
        }
        return false;
    }

    @Override
    public List<ObjectNode> loadSecurityQuestions() {

        try{

            List<ObjectNode> list = new ArrayList<>();

            for (SecurityQuestionEntity qn: securityQuestionsRepo.findAll()) {
                ObjectNode node = mObjectMapper.createObjectNode();

                node.put("id",qn.getId());
                node.put("title",qn.getTitle());
                node.putPOJO("options", qn.getOptions());
                node.put("type",qn.getType().toString());

                list.add(node);

            }
            return list;
        }catch (Exception ex){
            mLogger.log(Level.SEVERE,ex.getMessage(),ex);
        }
        return null;
    }

    @Override
    public List<ObjectNode> loadUserSecurityQuestions(String staffNo) {

        try{
            UserAccountEntity account = userRepository.findByStaffNo(staffNo).get();

            List<ObjectNode> list = new ArrayList<>();
            for (SecurityQuestionAnswerEntity ans:
                 securityQuestionAnswersRepo.findAllByUserIdAndStatus(account.getId(),Status.ACTIVE)) {
                //
                SecurityQuestionEntity qn = securityQuestionsRepo.findById(ans.getQuestionId()).get();
                //

                ObjectNode node = mObjectMapper.createObjectNode();
                node.put("id",qn.getId());
                node.put("title",qn.getTitle());

                node.put("type",qn.getType().toString());
                node.putPOJO("options",qn.getOptions());
                //
                list.add(node);
            }
            return list;
        }catch (Exception ex){
            mLogger.log(Level.SEVERE,ex.getMessage(),ex);
        }
        return null;
    }

    @Override
    public boolean setSecurityQuestions(SetSecurityQnsRequest model) {

        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            //
            UserAccountEntity account = userRepository.findByStaffNo(username).get();
            //
            for (SecQnAnswerReq qna: model.getAnswers()) {
                //
                SecurityQuestionEntity qn = securityQuestionsRepo.findByIdAndStatus(qna.getQnId(), Status.ACTIVE).get();
                //
                SecurityQuestionAnswerEntity ans =  new SecurityQuestionAnswerEntity();
                ans.setAnswer(qna.getAnswer());
                ans.setQuestionId(qn.getId());
                ans.setUserId(account.getId());
//                if(qn.getType() == SecurityQuestionType.SELECT_OPTIONS){
//                    //
//                    ans.s
//                }

                securityQuestionAnswersRepo.save(ans);
            }
            return  true;
        }catch (Exception ex){
            mLogger.log(Level.SEVERE,ex.getMessage(),ex);
        }

        return false;
    }

    @Override
    public boolean validateSecurityQuestions(ValidateSecurityQnsRequest model) {
        //
        boolean valid = false;

        try{
            UserAccountEntity account = userRepository.findByStaffNo(model.getStaffNo()).get();

            //
            for (SecQnAnswerReq prob:  model.getAnswers()) {
                //
                Optional<SecurityQuestionAnswerEntity> ans = securityQuestionAnswersRepo.findAllByIdAndUserIdAndStatus(account.getId(), account.getId(),Status.ACTIVE);

                if(ans.isPresent()){
                    valid = ans.get().getAnswer().equalsIgnoreCase(prob.getAnswer());
                }
            }
            //
            String code = smsService.sendSecurityCode(account.getStaffNo(),AuthCodeType.ONE_TIME_PIN);
            if(code != null){
                valid = false;
            }else {
                //
                account.setShouldSetPIN(true);
                //
                userRepository.save(account);//save user to db
            }
        }catch (Exception ex){
            mLogger.log(Level.SEVERE,ex.getMessage(),ex);
        }

        return valid;
    }

    @Override
    public boolean attemptCreatePIN(CreatePINRequest model) {

        try{
            Optional<UserAccountEntity> optionalUserAccount = userRepository.findByStaffNoAndPhoneNumber(model.getStaffNo(),model.getPhoneNo());

            if(optionalUserAccount.isPresent()){

                UserAccountEntity account = optionalUserAccount.get();
                if(account.getShouldSetPIN()){
                    //
                    account.setPassword(passwordEncoder.encode(model.getNewPIN()));
                    account.setShouldSetPIN(false);
                    account.setLastModified(Calendar.getInstance().getTime());
                    userRepository.save(account);//save user to db
                    return true;
                }
            }else{
                //User not found ..
            }
        }catch (Exception ex){
            mLogger.log(Level.SEVERE,ex.getMessage(),ex);
        }
        return false;
    }

    @Override
    public boolean attemptChangePassword(ChangePasswordRequest model) {

        try {
            //User should be authenticated,,
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
//            Object principal = SecurityContextHolder. getContext(). getAuthentication(). getPrincipal();
//            if (principal instanceof UserDetails) {
//                username = ((UserDetails)principal). getUsername();
//            } else {
//                username = principal. toString();
//            }
            //
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,model.getCurrentPassword()));

            //Update Login info ..
            UserAccountEntity account = userRepository.findByStaffNo(username).get();

            account.setPassword(passwordEncoder.encode(model.getNewPassword()));
            userRepository.save(account);//save user to db

            return true;
        }catch (Exception ex){
            mLogger.log(Level.SEVERE,"Change pin/password attempt failed.", ex);
        }
        return false;
    }

    @Override
    public boolean resetDSRPIN(ResetDSRPINRequest model) {

        try {

            UserAccountEntity account = userRepository.findByStaffNo(model.getStaffNo()).get();

            String pin = smsService.sendSecurityCode(account.getStaffNo(),AuthCodeType.ONE_TIME_PIN);
            //Send PIN
            if(pin != null){
                //Update flag
                account.setShouldSetPIN(true);
                //
                userRepository.save(account);//save user to db

                return true;
            }

        }catch (Exception ex){
            mLogger.log(Level.SEVERE,"PIN reset attempt failed.", ex);
        }
        return false;
    }

    @Override
    public boolean changeDSRPhoneNo(ChangeDSRPhoneNoRequest model) {

        try {

            UserAccountEntity account = userRepository.findByStaffNo(model.getStaffNo()).get();

            account.setPhoneNumber(model.getPhoneNo());

            userRepository.save(account);

            return true;
        }catch (Exception ex){
            mLogger.log(Level.SEVERE,"Change PIN/password attempt failed.", ex);
        }
        return false;
    }


}
