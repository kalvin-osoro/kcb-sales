package com.ekenya.rnd.backend.fskcb.AuthModule.controllers;

import com.ekenya.rnd.backend.fskcb.AuthModule.models.reqs.ChangeDSRPhoneNoRequest;
import com.ekenya.rnd.backend.fskcb.AuthModule.models.reqs.ChangePasswordRequest;
import com.ekenya.rnd.backend.fskcb.AuthModule.models.reqs.LoginRequest;
import com.ekenya.rnd.backend.fskcb.AuthModule.models.reqs.ResetDSRPINRequest;
import com.ekenya.rnd.backend.fskcb.AuthModule.models.resp.LoginResponse;
import com.ekenya.rnd.backend.fskcb.AuthModule.services.IAuthService;
import com.ekenya.rnd.backend.responses.AppResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@Api(value = "Auth Controller for Field Agent Rest Api")
@RestController
@RequestMapping(path = "/api/v1")
public class AuthPortalController {
    @Autowired
    private IAuthService authService;


    @PostMapping("/signin")
    @ApiOperation(value = "Login Api")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest){
        LoginResponse resp = authService.attemptLogin(loginRequest);

        ObjectMapper objectMapper = new ObjectMapper();
        if(resp != null){

            return ResponseEntity.ok(new AppResponse(1,resp,"User login successful"));
        }
        return ResponseEntity.ok(new AppResponse(1,objectMapper.createObjectNode(),"User login failed"));

    }


    @PostMapping("/change-password")
    @ApiOperation(value = "Change Password Api")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordRequest req){

        boolean success = authService.attemptChangePassword(req);

        ObjectMapper objectMapper = new ObjectMapper();
        if(success){
            //
            return ResponseEntity.ok(new AppResponse(1,objectMapper.createObjectNode(),"Request processed successful"));
        }
        return ResponseEntity.ok(new AppResponse(1,objectMapper.createObjectNode(),"Request Failed"));

    }


    @PostMapping("/reset-dsr-pin")
    @ApiOperation(value = "Reset DSR PIN Api")
    public ResponseEntity<?> resetDSRPIN(@RequestBody ResetDSRPINRequest req){

        boolean success = authService.resetDSRPIN(req);

        ObjectMapper objectMapper = new ObjectMapper();
        if(success){
            //
            return ResponseEntity.ok(new AppResponse(1,objectMapper.createObjectNode(),"Request processed successful"));
        }
        return ResponseEntity.ok(new AppResponse(1,objectMapper.createObjectNode(),"Request Failed"));

    }


    @PostMapping("/change-dsr-phone-no")
    @ApiOperation(value = "Change DSR Phone No. Api")
    public ResponseEntity<?> changeDSRPhoneNo(@RequestBody ChangeDSRPhoneNoRequest req){

        boolean success = authService.changeDSRPhoneNo(req);

        ObjectMapper objectMapper = new ObjectMapper();
        if(success){
            //
            return ResponseEntity.ok(new AppResponse(1,objectMapper.createObjectNode(),"Request processed successful"));
        }
        return ResponseEntity.ok(new AppResponse(0,objectMapper.createObjectNode(),"Request Failed"));

    }
}