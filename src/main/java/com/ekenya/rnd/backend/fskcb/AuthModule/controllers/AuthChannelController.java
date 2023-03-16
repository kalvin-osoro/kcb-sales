package com.ekenya.rnd.backend.fskcb.AuthModule.controllers;

import com.ekenya.rnd.backend.fskcb.AuthModule.datasource.entities.LoginLogs;
import com.ekenya.rnd.backend.fskcb.AuthModule.datasource.repositories.LoginLogsRepository;
import com.ekenya.rnd.backend.fskcb.AuthModule.models.reqs.*;
import com.ekenya.rnd.backend.fskcb.AuthModule.models.resp.AccountLookupState;
import com.ekenya.rnd.backend.fskcb.AuthModule.models.resp.LoginResponse;
import com.ekenya.rnd.backend.fskcb.AuthModule.services.IAuthService;
import com.ekenya.rnd.backend.fskcb.UserManagement.services.IUsersService;
import com.ekenya.rnd.backend.responses.BaseAppResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;

@CrossOrigin(origins = "*")
@Api(value = "Auth Controller for Field Agent Rest Api")
@RestController
@Validated
@RequestMapping(path = "/api/v1/ch")
public class AuthChannelController {
    @Autowired
    DateFormat dateFormat;

    @Autowired
    LoginLogsRepository loginLogsRepository;
    @Autowired
    IAuthService authService;
    @Autowired
    IUsersService usersService;
    @Autowired
    ObjectMapper mObjectMapper;
    public AuthChannelController() {
    }

    @PostMapping("/login")
    @Validated
    @ApiOperation(value = "Login Api")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody ChannelLoginRequest request){
        //Result result = loginRequest.validate();

//
        LoginResponse resp = authService.attemptChannelLogin(request);
        //
        if(resp != null) {
            //
            if (resp.isSuccess()) {
                //
                ObjectNode node = mObjectMapper.createObjectNode();
                node.put("token",resp.getToken());
                node.put("dsrId",resp.getDsrId());
                node.put("type",resp.getType());
                node.put("name",resp.getName());
                node.put("email",resp.getEmail());
                node.put("sales_code",resp.getSalesCode());
                node.put("team_name",resp.getTeamName());
                node.put("team_code",resp.getTeamCode());
                node.put("isRm",resp.isRm()? 1:0);
                node.put("issued",dateFormat.format(resp.getIssued()));
                node.put("expires_in",resp.getExpiresInMinutes());
                node.putPOJO("profiles",resp.getProfiles());
                node.put("changePin",resp.isShouldChangePin() ? 1 : 0);
                node.put("setSecQns",resp.isShouldSetSecQns() ? 1 : 0);
                //
                loginTrail(resp.getName(),true,"app",resp.getProfiles()==null? null :resp.getProfiles());
                return ResponseEntity.ok(new BaseAppResponse(1, node, "Request processed successful"));

            }else if(resp.getErrorMessage() != null){

                return ResponseEntity.ok(new BaseAppResponse(0,mObjectMapper.createObjectNode(),resp.getErrorMessage()));
            }else if(resp.getRemAttempts() > 0){
                //
                ObjectNode node = mObjectMapper.createObjectNode();
                node.put("remAttempts",resp.getRemAttempts());
                //
                return ResponseEntity.ok(new BaseAppResponse(0,node,"Login Attempt Failed. \r\nYou "+resp.getRemAttempts()+" remaining attempt(s)"));

            }
        }
        //
        return ResponseEntity.ok(new BaseAppResponse(0,mObjectMapper.createObjectNode(),"Request could NOT be processed. Please try again later"));

    }

    @PostMapping("/create-pin")
    @ApiOperation(value = "Create PIN Api")
    public ResponseEntity<?> createPIN(@RequestBody CreatePINRequest request){

        boolean success = authService.attemptCreatePIN(request);

        ObjectMapper objectMapper = new ObjectMapper();
        if(success){

            return ResponseEntity.ok(new BaseAppResponse(1,objectMapper.createObjectNode(),"User PIN created successful"));
        }
        return ResponseEntity.ok(new BaseAppResponse(1,objectMapper.createObjectNode(),"Request could NOT be processed. Please try again later"));

    }


    @PostMapping("/change-pin")
    @ApiOperation(value = "Change PIN Api")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> changePIN(@RequestBody ChangePINRequest request){

        boolean success = authService.attemptChangePIN(request);

        ObjectMapper objectMapper = new ObjectMapper();
        if(success){
            //
            return ResponseEntity.ok(new BaseAppResponse(1,objectMapper.createObjectNode(),"User PIN changed successful"));
        }
        return ResponseEntity.ok(new BaseAppResponse(1,objectMapper.createObjectNode(),"Request could NOT be processed. Please try again later"));

    }

    @PostMapping("/account-lookup")
    @ApiOperation(value = "Account Lookup Api, Registered = 0 - non_existing DSR Accounts, 2 - existing DSR Account who have never used the app, 1 - for existing DSR accounts who have been using the app")
    public ResponseEntity<?> lookupUser(@RequestBody LookupRequest model) {

        //
        AccountLookupState status = authService.accountExists(model);

        ObjectMapper objectMapper = new ObjectMapper();
        if(status != null){
            //Response
            ObjectNode node = objectMapper.createObjectNode();
            //
            node.put("registered",status.getState());
            //
            return ResponseEntity.ok(new BaseAppResponse(1,node,"Request processed successfully"));
        }
        //Response
        //
        return ResponseEntity.ok(new BaseAppResponse(0,objectMapper.createObjectNode(),"Request could NOT be processed. Please try again later"));
    }


    @PostMapping("/send-device-verification-code")
    @ApiOperation(value = "Device Verification Code Api")
    public ResponseEntity<?> sendPhoneVerificationCode(@RequestBody SendVerificationCodeRequest model) {

        //
        String code = authService.sendVerificationCode(model);

        if(code != null){
            //Response
            ObjectNode node = mObjectMapper.createObjectNode();
            node.put("code",code);
            //
            return ResponseEntity.ok(new BaseAppResponse(1,node,"Request processed successfully"));
        }
        //Response
        //
        return ResponseEntity.ok(new BaseAppResponse(0,mObjectMapper.createObjectNode(),"Request could NOT be processed. Please try again later"));
    }
    @PostMapping("/validate-device-verification-code")
    @ApiOperation(value = "validate-device-verification-code")
    public ResponseEntity<?> validatePhoneVerificationCode(@RequestBody ValidateVerificationCodeRequest model) {

        //
        boolean valid = authService.validateVerificationCode(model);

        ObjectMapper objectMapper = new ObjectMapper();
        //Response
        ObjectNode node = objectMapper.createObjectNode();
        //
        node.put("valid",valid ? 1 : 0);
        //
        return ResponseEntity.ok(new BaseAppResponse(1,node,"Request processed successfully"));

    }
    @PostMapping("/get-security-questions")
    @ApiOperation(value = "Get Security questions")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getSecurityQuestions() {

        //
        List<?> list = authService.loadSecurityQuestions();

        if(list != null){
            //Response
            //
            return ResponseEntity.ok(new BaseAppResponse(1,list,"Request processed successfully"));
        }
        return ResponseEntity.ok(new BaseAppResponse(0,mObjectMapper.createArrayNode(),"Request could NOT be processed. Please try again later"));

    }
    @PostMapping("/set-security-questions")
    @ApiOperation(value = "Set User Security Questions")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> setSecurityQuestions(@RequestBody SetSecurityQnsRequest model) {

        //
        boolean success = authService.setSecurityQuestions(model);

        if(success){
            //
            return ResponseEntity.ok(new BaseAppResponse(1,mObjectMapper.createObjectNode(),"Request processed successfully"));
        }
        //
        return ResponseEntity.ok(new BaseAppResponse(0,mObjectMapper.createArrayNode(),"Request could NOT be processed. Please try again later"));
    }
    @PostMapping("/update-security-questions")
    @ApiOperation(value = "Update User security Questions")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> updateSecurityQuestions(@RequestBody UpdateSecurityQnsRequest model) {

        //
        boolean success = authService.updateSecurityQuestions(model);

        if(success){
            //
            return ResponseEntity.ok(new BaseAppResponse(1,mObjectMapper.createObjectNode(),"Request processed successfully"));
        }
        //
        return ResponseEntity.ok(new BaseAppResponse(0,mObjectMapper.createArrayNode(),"Request could NOT be processed. Please try again later"));
    }
    @PostMapping("/get-user-security-questions")
    @ApiOperation(value = "Get Security questions")
    public ResponseEntity<?> getUserSecurityQuestions(@RequestBody UserSecurityQuestionsRequest request) {

        //
        List<?> list = authService.loadUserSecurityQuestions(request.getStaffNo());

        if(list != null){
            //Response
            //
            return ResponseEntity.ok(new BaseAppResponse(1,list,"Request processed successfully"));
        }
        return ResponseEntity.ok(new BaseAppResponse(0,mObjectMapper.createArrayNode(),"Request could NOT be processed. Please try again later"));

    }
    @PostMapping("/validate-security-questions")
    @ApiOperation(value = "")
    public ResponseEntity<?> validateSecurityQuestions(@RequestBody ValidateSecurityQnsRequest model) {

        //
        boolean success = authService.validateSecurityQuestions(model);

        if(success){
            //
            return ResponseEntity.ok(new BaseAppResponse(1,mObjectMapper.createObjectNode(),"Request processed successfully"));
        }
        //
        return ResponseEntity.ok(new BaseAppResponse(0,mObjectMapper.createArrayNode(),"Request could NOT be processed. Please try again later"));
    }

    public  void loginTrail(String username, boolean loginStatus, String channel, ArrayNode profiles) {
        LoginLogs loginTrailEntity = new LoginLogs();
        loginTrailEntity.setFullName(username);
//        loginTrailEntity.setIpAddress(ipAddress);
        loginTrailEntity.setSuccessful(loginStatus);
        loginTrailEntity.setChannel(channel);
//        loginTrailEntity.setLoginMessage(loginMessage);
        loginTrailEntity.setLoginDate(new Date());
        loginLogsRepository.save(loginTrailEntity);
    }
}