package com.ekenya.rnd.backend.fskcb.QSSAdapter.controllers;

import com.ekenya.rnd.backend.fskcb.DSRModule.models.reqs.AddTeamRequest;
import com.ekenya.rnd.backend.fskcb.QSSAdapter.models.QssAlertRequest;
import com.ekenya.rnd.backend.fskcb.QSSAdapter.services.IQssService;
import com.ekenya.rnd.backend.responses.AppResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1")
public class QssTestRestController {

    @Autowired
    IQssService qssService;

    @PostMapping("/qss-send-alert")
    public ResponseEntity<?> createTeam(@RequestBody QssAlertRequest request ) {

        boolean success = qssService.sendAlert(request.getReceiver(),request.getTitle(), request.getMessage(),request.getCategory());

        //Response
        ObjectMapper objectMapper = new ObjectMapper();
        if(success){
            //Object
            ObjectNode node = objectMapper.createObjectNode();
//          node.put("id",0);

            return ResponseEntity.ok(new AppResponse(1,node,"Request Processed Successfully"));
        }else{

            //Response
            return ResponseEntity.ok(new AppResponse(0,objectMapper.createObjectNode(),"Request could NOT be processed. Please try again later"));
        }
    }

    @PostMapping("/qss-get-online-users")
    public ResponseEntity<?> getOnlineUsers() {


        //Response
        return ResponseEntity.ok(new AppResponse(1,qssService.getOnlineUsers(),"Request Processed Successfully"));
    }
}
