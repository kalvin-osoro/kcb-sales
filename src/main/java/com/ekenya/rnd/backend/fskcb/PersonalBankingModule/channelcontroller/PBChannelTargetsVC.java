package com.ekenya.rnd.backend.fskcb.PersonalBankingModule.channelcontroller;


import com.ekenya.rnd.backend.fskcb.AcquringModule.services.IAcquiringPortalService;
import com.ekenya.rnd.backend.fskcb.PersonalBankingModule.services.IPBChannelService;
import com.ekenya.rnd.backend.responses.BaseAppResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping(path = "/api/v1/ch")
public class PBChannelTargetsVC {


    @Autowired
    IPBChannelService channelService;
    @PostMapping("/pb-get-targets-summary")
    public ResponseEntity<?> getTargetsSummary() {

        //Resp =>
        //{
        //    "onboarding":{
        //        "achieved":67,
        //        "target":100
        //    },
        //    "visits":{
        //        "achieved":67,
        //        "target":100
        //    },
        //    "leads":{
        //        "achieved":67,
        //        "target":100
        //    },
        //    "campaigns":{
        //        "achieved":67,
        //        "target":100
        //    },
        //    "cur-comission":56000,
        //    "prev-comission":45000
        //}
        ArrayList<?> targets = channelService.getTargetsSummary();
        boolean success = targets != null;
        //Response
        ObjectMapper objectMapper = new ObjectMapper();
        if(success){
            //Object
            ArrayNode node = objectMapper.createArrayNode();
            node.addAll((ArrayList) targets);

            return ResponseEntity.ok(new BaseAppResponse(1,node,"Request Processed Successfully"));
        }else{

            //Response
            return ResponseEntity.ok(new BaseAppResponse(0,objectMapper.createObjectNode(),"Request could NOT be processed. Please try again later"));
        }
    }
}
