package com.ekenya.rnd.backend.fskcb.AuthModule.controllers;

import com.ekenya.rnd.backend.fskcb.AuthModule.models.reqs.*;
import com.ekenya.rnd.backend.fskcb.AuthModule.models.resp.LoginResponse;
import com.ekenya.rnd.backend.fskcb.AuthModule.services.IAuthService;
import com.ekenya.rnd.backend.responses.AppResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@Api(value = "Auth Controller for Field Agent Rest Api")
@RestController
@RequestMapping(path = "/api/v1/ch")
public class AuthChannelController {

    @Autowired
    IAuthService authService;

    public AuthChannelController() {
    }

    @PostMapping("/login")
    @ApiOperation(value = "Login Api")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest){

        LoginResponse resp = authService.attemptLogin(loginRequest);

        ObjectMapper objectMapper = new ObjectMapper();
        if(resp != null){

            return ResponseEntity.ok(new AppResponse(1,resp,"User login successful"));
        }
        return ResponseEntity.ok(new AppResponse(1,objectMapper.createObjectNode(),"User login failed"));

    }

    @PostMapping("/create-pin")
    @ApiOperation(value = "Create PIN Api")
    public ResponseEntity<?> createPIN(@RequestBody CreatePINRequest request){

        boolean success = authService.attemptCreatePIN(request);

        ObjectMapper objectMapper = new ObjectMapper();
        if(success){

            return ResponseEntity.ok(new AppResponse(1,objectMapper.createObjectNode(),"User login successful"));
        }
        return ResponseEntity.ok(new AppResponse(1,objectMapper.createObjectNode(),"User login failed"));

    }


    @PostMapping("/change-pin")
    @ApiOperation(value = "Change PIN Api")
    public ResponseEntity<?> changePIN(@RequestBody ChangePINRequest request){

        boolean success = authService.attemptChangePIN(request);

        ObjectMapper objectMapper = new ObjectMapper();
        if(success){
            //
            return ResponseEntity.ok(new AppResponse(1,objectMapper.createObjectNode(),"User login successful"));
        }
        return ResponseEntity.ok(new AppResponse(1,objectMapper.createObjectNode(),"User login failed"));

    }

    @PostMapping("/account-lookup")
    @ApiOperation(value = "Login Api")
    public ResponseEntity<?> lookupUser(@RequestBody LookupRequest model) {

        //
        int status = authService.accountExists(model);

        ObjectMapper objectMapper = new ObjectMapper();
        if(status >=0){
            //Response
            ObjectNode node = objectMapper.createObjectNode();
            //
            node.put("registered",status);
            //
            return ResponseEntity.ok(new AppResponse(1,node,"Request processed successfully"));
        }
        //Response
        //
        return ResponseEntity.ok(new AppResponse(1,objectMapper.createObjectNode(),"Request Failed"));
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
            return ResponseEntity.ok(new AppResponse(1,node,"Request processed successfully"));
        }
        //Response
        //
        return ResponseEntity.ok(new AppResponse(1,objectMapper.createObjectNode(),"Request Failed"));
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
        return ResponseEntity.ok(new AppResponse(1,node,"Request processed successfully"));

    }
}