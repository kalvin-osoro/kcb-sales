package com.ekenya.rnd.backend.fskcb.setup.controller;

import com.ekenya.rnd.backend.fskcb.AcquringModule.models.AcquiringAddTargetRequest;
import com.ekenya.rnd.backend.fskcb.setup.model.EmailWrapper;
import com.ekenya.rnd.backend.fskcb.setup.service.EmailService;
import com.ekenya.rnd.backend.responses.BaseAppResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class EmailVC {
    private final EmailService emailService;


    @PostMapping("/create-business-unit-email")
    public ResponseEntity<?> createSetupEmails(@RequestBody EmailWrapper emailWrapper) {
        boolean success = emailService.addNewEmail(emailWrapper);
        //Response
        ObjectMapper objectMapper = new ObjectMapper();
        if(success){
            //Object
            ObjectNode node = objectMapper.createObjectNode();
//          node.put("id",0);

            return ResponseEntity.ok(new BaseAppResponse(1,node,"Request Processed Successfully"));
        }else{

            //Response
            return ResponseEntity.ok(new BaseAppResponse(0,objectMapper.createObjectNode(),"Request could NOT be processed. Please try again later"));
        }
    }
}
