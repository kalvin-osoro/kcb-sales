package com.ekenya.rnd.backend.fskcb.AuthModule.controllers;

import com.ekenya.rnd.backend.fskcb.AuthModule.models.reqs.*;
import com.ekenya.rnd.backend.fskcb.AuthModule.models.resp.AccountLookupState;
import com.ekenya.rnd.backend.fskcb.AuthModule.models.resp.LoginResponse;
import com.ekenya.rnd.backend.fskcb.AuthModule.services.IAuthService;
import com.ekenya.rnd.backend.fskcb.UserManagement.datasource.entities.AccountType;
import com.ekenya.rnd.backend.fskcb.UserManagement.datasource.entities.UserAccount;
import com.ekenya.rnd.backend.fskcb.UserManagement.services.IUsersService;
import com.ekenya.rnd.backend.responses.BaseAppResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest){
        //Result result = loginRequest.validate();

        UserAccount account = usersService.findByStaffNo(loginRequest.getStaffNo());
        if(account.getAccountType() == AccountType.DSR){
            //
            LoginResponse resp = authService.attemptLogin(loginRequest);
            //
            if(resp != null) {
                //
                if (resp.isSuccess()) {
                    //
                    ObjectNode node = mObjectMapper.createObjectNode();
                    node.put("token",resp.getToken());
                    node.put("type",resp.getType());
                    node.put("expires_in",dateFormat.format(resp.getExpires()));
                    node.putPOJO("profiles",resp.getProfiles());
                    //
                    return ResponseEntity.ok(new BaseAppResponse(1, node, "Request processed successful"));

                }else if(resp.getRemAttempts() >0){
                    //
                    return ResponseEntity.ok(new BaseAppResponse(0,mObjectMapper.createObjectNode(),"Login Failed. \nYou "+resp.getRemAttempts()+" remaining attempt(s)"));

                }
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
        boolean status = authService.sendVerificationCode(model);

        ObjectMapper objectMapper = new ObjectMapper();
        if(status){
            //Response
            ObjectNode node = objectMapper.createObjectNode();
            //
            return ResponseEntity.ok(new BaseAppResponse(1,node,"Request processed successfully"));
        }
        //Response
        //
        return ResponseEntity.ok(new BaseAppResponse(0,objectMapper.createObjectNode(),"Request could NOT be processed. Please try again later"));
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
    @ApiOperation(value = "")
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
    @PostMapping("/get-user-security-questions")
    @ApiOperation(value = "Get Security questions")
    public ResponseEntity<?> getUserSecurityQuestions(@RequestBody String staffNo) {

        //
        List<?> list = authService.loadUserSecurityQuestions(staffNo);

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
}