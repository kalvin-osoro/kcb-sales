package com.ekenya.rnd.backend.fskcb.AuthModule.controllers;

import com.ekenya.rnd.backend.fskcb.AuthModule.datasource.entities.LoginLogs;
import com.ekenya.rnd.backend.fskcb.AuthModule.datasource.repositories.LoginLogsRepository;
import com.ekenya.rnd.backend.fskcb.AuthModule.models.reqs.*;
import com.ekenya.rnd.backend.fskcb.AuthModule.models.resp.LoginResponse;
import com.ekenya.rnd.backend.fskcb.AuthModule.services.IAuthService;
import com.ekenya.rnd.backend.responses.BaseAppResponse;
import com.ekenya.rnd.backend.utils.Utility;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.text.DateFormat;
import java.util.Date;

@CrossOrigin(origins = "*")
@Api(value = "Auth Controller for Field Agent Rest Api")
@RestController
@RequestMapping(path = "/api/v1")
public class AuthPortalController {
    @Autowired
    private IAuthService authService;
    @Autowired
    DateFormat dateFormat;

    @Autowired
    LoginLogsRepository loginLogsRepository;

    @Autowired
    ObjectMapper mObjectMapper;

    @PostMapping("/signin")
    @ApiOperation(value = "Login Api")
    public ResponseEntity<?> loginUser(@RequestBody PortalLoginRequest request){

        LoginResponse resp = authService.attemptAdminLogin(request);

        if(resp != null) {
            //
            if(resp.isSuccess()){

                ObjectNode node = mObjectMapper.createObjectNode();
                node.put("token",resp.getToken());
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
                //
                loginTrail(resp.getName(),true,"portal",resp.getProfiles()==null ? null:resp.getProfiles());
                //
                return ResponseEntity.ok(new BaseAppResponse(1,node,"User login successful"));
            }else if(resp.getErrorMessage() != null){

                return ResponseEntity.ok(new BaseAppResponse(1,mObjectMapper.createObjectNode(),resp.getErrorMessage()));
            }
        }
        return ResponseEntity.ok(new BaseAppResponse(1,mObjectMapper.createObjectNode(),"User login failed"));

    }


    @PostMapping("/change-password")
    @ApiOperation(value = "Change Password Api")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordRequest req){

        boolean success = authService.attemptChangePassword(req);

        ObjectMapper objectMapper = new ObjectMapper();
        if(success){
            //
            return ResponseEntity.ok(new BaseAppResponse(1,objectMapper.createObjectNode(),"Request processed successful"));
        }
        return ResponseEntity.ok(new BaseAppResponse(1,objectMapper.createObjectNode(),"Request Failed"));

    }


    @PostMapping("/reset-dsr-pin")
    @ApiOperation(value = "Reset DSR PIN Api")
    public ResponseEntity<?> resetDSRPIN(@RequestBody ResetDSRPINRequest req){

        boolean success = authService.resetDSRPIN(req);

        ObjectMapper objectMapper = new ObjectMapper();
        if(success){
            //
            return ResponseEntity.ok(new BaseAppResponse(1,objectMapper.createObjectNode(),"Request processed successful"));
        }
        return ResponseEntity.ok(new BaseAppResponse(1,objectMapper.createObjectNode(),"Request Failed"));

    }


    @PostMapping("/change-dsr-phone-no")
    @ApiOperation(value = "Change DSR Phone No. Api")
    public ResponseEntity<?> changeDSRPhoneNo(@RequestBody ChangeDSRPhoneNoRequest req){

        boolean success = authService.changeDSRPhoneNo(req);

        ObjectMapper objectMapper = new ObjectMapper();
        if(success){
            //
            return ResponseEntity.ok(new BaseAppResponse(1,objectMapper.createObjectNode(),"Request processed successful"));
        }
        return ResponseEntity.ok(new BaseAppResponse(0,objectMapper.createObjectNode(),"Request Failed"));

    }



    @PostMapping("/user-forgot-password")
    @ApiOperation(value = "Send recovery password to email or phone no.")
    public ResponseEntity<?> forgotPassword(@RequestBody ForgotPasswordRequest req){

        boolean success = authService.attemptRecoverPassword(req);

        ObjectMapper objectMapper = new ObjectMapper();
        if(success){
            //
            return ResponseEntity.ok(new BaseAppResponse(1,objectMapper.createObjectNode(),"Request processed successful"));
        }
        return ResponseEntity.ok(new BaseAppResponse(0,objectMapper.createObjectNode(),"Request Failed"));

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