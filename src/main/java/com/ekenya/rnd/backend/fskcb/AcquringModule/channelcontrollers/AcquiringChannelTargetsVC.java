package com.ekenya.rnd.backend.fskcb.AcquringModule.channelcontrollers;


import com.ekenya.rnd.backend.fskcb.AcquringModule.services.IAcquiringChannelService;
import com.ekenya.rnd.backend.responses.BaseAppResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/ch")
public class AcquiringChannelTargetsVC {


    @Autowired
    IAcquiringChannelService acquiringService;
    @PostMapping("/acquiring-get-targets-summary")
    public ResponseEntity<?> getTargetsSummary() {
        List<?> targets = acquiringService.getTargetsSummary();
        boolean success = targets  != null;

        //Response
        ObjectMapper objectMapper = new ObjectMapper();
        if(success){
            //Object
           ArrayNode node = objectMapper.createArrayNode();
           node.addAll((List)targets);
            return ResponseEntity.ok(new BaseAppResponse(1,node,"Request Processed Successfully"));
        }else{

            //Response
            return ResponseEntity.ok(new BaseAppResponse(0,objectMapper.createObjectNode(),"Request could NOT be processed. Please try again later"));
        }
    }


}
