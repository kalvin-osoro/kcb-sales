package com.ekenya.rnd.backend.fskcb.AuthModule.services;

import com.ekenya.rnd.backend.fskcb.AuthModule.datasource.entities.*;
import com.ekenya.rnd.backend.fskcb.AuthModule.datasource.repositories.ISecurityAuthCodesRepository;
import com.ekenya.rnd.backend.fskcb.AuthModule.datasource.repositories.ISecurityQuestionAnswersRepo;
import com.ekenya.rnd.backend.fskcb.AuthModule.datasource.repositories.ISecurityQuestionsRepo;
import com.ekenya.rnd.backend.fskcb.AuthModule.models.reqs.*;
import com.ekenya.rnd.backend.fskcb.AuthModule.models.resp.AccountLookupState;
import com.ekenya.rnd.backend.fskcb.AuthModule.models.resp.LoginResponse;
import com.ekenya.rnd.backend.fskcb.DSRModule.datasource.repositories.IDSRAccountsRepository;
import com.ekenya.rnd.backend.fskcb.UserManagement.datasource.entities.SystemRoles;
import com.ekenya.rnd.backend.fskcb.UserManagement.datasource.entities.UserAccount;
import com.ekenya.rnd.backend.fskcb.UserManagement.datasource.repositories.UserProfilesRepository;
import com.ekenya.rnd.backend.fskcb.UserManagement.datasource.repositories.RoleRepository;
import com.ekenya.rnd.backend.fskcb.UserManagement.datasource.repositories.UserRepository;
import com.ekenya.rnd.backend.fskcb.UserManagement.security.JwtTokenProvider;
import com.ekenya.rnd.backend.utils.Status;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
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
import java.util.stream.Collectors;

@Service
@Slf4j
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
    ISecurityQuestionAnswersRepo securityQuestionAnswersRepo;
    @Autowired
    ISecurityQuestionsRepo securityQuestionsRepo;

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
            UserAccount account = userRepository.findByStaffNo(username).get();
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
            Optional<UserAccount> optionalUserAccount = userRepository.findByStaffNo(model.getStaffNo());
            //Update ..
            if(optionalUserAccount.isPresent()){
                UserAccount userAccount = optionalUserAccount.get();
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
            log.error(ex.getMessage(),ex);
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
            UserAccount account = userRepository.findByStaffNo(username).get();

            account.setPassword(passwordEncoder.encode(model.getNewPIN()));
            userRepository.save(account);//save user to db

            return true;
        }catch (Exception ex){
            log.error("Change pin/password attempt failed.", ex);
        }
        return false;
    }

    @Override
    public AccountLookupState accountExists(LookupRequest model) {

        try {
            if(userRepository.findByStaffNoAndPhoneNumber(model.getStaffNo(), model.getPhoneNo()).isPresent()){
                //Account exists
                return AccountLookupState.ACTIVE;
            }else if(dsrAccountsRepository.findByStaffNo(model.getStaffNo()).isPresent()){
                //Not Active
                return AccountLookupState.NOT_ACTIVATED;
            }
            //Not found
            return AccountLookupState.NOT_FOUND;
        }catch (Exception ex){
            log.error(ex.getMessage(),ex);
        }

        return null;
    }

    @Override
    public boolean sendVerificationCode(SendVerificationCodeRequest model) {
        //
        try{

            //
            if(smsService.sendSecurityCode(model.getStaffNo(),AuthCodeType.DEVICE_VERIFICATION)){
                //
                return true;
            }
        }catch (Exception ex){
            log.error(ex.getMessage(),ex);
        }

        return false;
    }

    @Override
    public boolean validateVerificationCode(ValidateVerificationCodeRequest model) {
        //

        try{
            //
            UserAccount userAccount = userRepository.findByStaffNo(model.getStaffNo()).get();

            Optional<SecurityAuthCodeEntity> code = securityAuthCodesRepository.findAllByCode(model.getCode());
            if(code.isPresent() && code.get().getUserId() == userAccount.getId() && !code.get().isExpired() ){
                //
                LocalDateTime dateTime = LocalDateTime.ofInstant(code.get().getDateIssued().toInstant(),
                        ZoneId.systemDefault());
                //
                dateTime = dateTime.plusMinutes(code.get().getExpiresInMinutes());
                //
                if(!dateTime.isAfter(LocalDateTime.now())){
                    return true;
                }
            }
        }catch (Exception ex){
            log.error(ex.getMessage(),ex);
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
            log.error(ex.getMessage(),ex);
        }
        return null;
    }

    @Override
    public List<ObjectNode> loadUserSecurityQuestions(String staffNo) {

        try{
            UserAccount account = userRepository.findByStaffNo(staffNo).get();

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
            log.error(ex.getMessage(),ex);
        }
        return null;
    }

    @Override
    public boolean setSecurityQuestions(SetSecurityQnsRequest model) {

        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            //
            UserAccount account = userRepository.findByStaffNo(username).get();
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
            log.error(ex.getMessage(),ex);
        }

        return false;
    }

    @Override
    public boolean validateSecurityQuestions(ValidateSecurityQnsRequest model) {
        //
        boolean valid = false;

        try{
            UserAccount account = userRepository.findByStaffNo(model.getStaffNo()).get();

            //
            for (SecQnAnswerReq prob:  model.getAnswers()) {
                //
                Optional<SecurityQuestionAnswerEntity> ans = securityQuestionAnswersRepo.findAllByIdAndUserIdAndStatus(account.getId(), account.getId(),Status.ACTIVE);

                if(ans.isPresent()){
                    valid = ans.get().getAnswer().equalsIgnoreCase(prob.getAnswer());
                }
            }
            //
            if(!smsService.sendSecurityCode(account.getStaffNo(),AuthCodeType.ONE_TIME_PIN)){
                valid = false;
            }else {
                //
                account.setShouldSetPIN(true);
                //
                userRepository.save(account);//save user to db
            }
        }catch (Exception ex){
            log.error(ex.getMessage(),ex);
        }

        return valid;
    }

    @Override
    public boolean attemptCreatePIN(CreatePINRequest model) {

        try{
            Optional<UserAccount> optionalUserAccount = userRepository.findByStaffNoAndPhoneNumber(model.getStaffNo(),model.getPhoneNo());

            if(optionalUserAccount.isPresent()){

                UserAccount account = optionalUserAccount.get();
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
            log.error(ex.getMessage(),ex);
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
            UserAccount account = userRepository.findByStaffNo(username).get();

            account.setPassword(passwordEncoder.encode(model.getNewPassword()));
            userRepository.save(account);//save user to db

            return true;
        }catch (Exception ex){
            log.error("Change pin/password attempt failed.", ex);
        }
        return false;
    }

    @Override
    public boolean resetDSRPIN(ResetDSRPINRequest model) {

        try {

            UserAccount account = userRepository.findByStaffNo(model.getStaffNo()).get();

            //Send PIN
            if(smsService.sendSecurityCode(account.getStaffNo(),AuthCodeType.ONE_TIME_PIN)){
                //Update flag
                account.setShouldSetPIN(true);
                //
                userRepository.save(account);//save user to db

                return true;
            }

        }catch (Exception ex){
            log.error("PIN reset attempt failed.", ex);
        }
        return false;
    }

    @Override
    public boolean changeDSRPhoneNo(ChangeDSRPhoneNoRequest model) {

        try {

            UserAccount account = userRepository.findByStaffNo(model.getStaffNo()).get();

            account.setPhoneNumber(model.getPhoneNo());

            userRepository.save(account);

            return true;
        }catch (Exception ex){
            log.error("Change PIN/password attempt failed.", ex);
        }
        return false;
    }


}
