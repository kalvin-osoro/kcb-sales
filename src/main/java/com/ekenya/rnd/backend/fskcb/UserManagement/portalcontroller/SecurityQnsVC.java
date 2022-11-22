package com.ekenya.rnd.backend.fskcb.UserManagement.portalcontroller;

import com.ekenya.rnd.backend.fskcb.UserManagement.datasource.entities.SystemRoles;
import com.ekenya.rnd.backend.fskcb.UserManagement.models.reps.AddSecurityQnRequest;
import com.ekenya.rnd.backend.fskcb.UserManagement.services.IUsersService;
import com.ekenya.rnd.backend.responses.BaseAppResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "Upload User  Rest Api")
@CrossOrigin("*")
@RestController
@RequestMapping(path = "/api/v1")
@PreAuthorize("hasAuthority('"+ SystemRoles.SYS_ADMIN+"') or hasAuthority('"+SystemRoles.ADMIN+"')")
public class SecurityQnsVC {


    @Autowired
    ObjectMapper mObjectMapper;
    @Autowired
    IUsersService usersService;

    @PostMapping("/users-create-sec-qn")
    public ResponseEntity<?> createSecurityQns(@RequestBody AddSecurityQnRequest request ) {
        //INSIDE SERVICE
        boolean success = usersService.attemptCreateSecurityQuestion(request);
        if(success){
            //Object
            ObjectNode node = mObjectMapper.createObjectNode();

            return ResponseEntity.ok(new BaseAppResponse(1,node,"Request Processed Successfully"));
        }else{

            //Response
            return ResponseEntity.ok(new BaseAppResponse(0,mObjectMapper.createObjectNode(),"Request could NOT be processed. Please try again later"));
        }
    }

    @PostMapping(value = "/users-get-sec-qns")
    public ResponseEntity<?> getAllSecurityQuestions() {


        //INSIDE SERVICE
        List<?> list = usersService.loadAllSecurityQuestions();
        //Response
        if(list != null){
            //Object

            return ResponseEntity.ok(new BaseAppResponse(1,list,"Request Processed Successfully"));
        }else{

            //Response
            return ResponseEntity.ok(new BaseAppResponse(0,mObjectMapper.createArrayNode(),"Request could NOT be processed. Please try again later"));
        }
    }
}
