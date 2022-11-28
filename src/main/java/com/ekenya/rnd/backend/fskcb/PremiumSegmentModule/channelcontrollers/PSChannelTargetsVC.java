package com.ekenya.rnd.backend.fskcb.PremiumSegmentModule.channelcontrollers;


import com.ekenya.rnd.backend.fskcb.PremiumSegmentModule.services.IPSChannelService;
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
public class PSChannelTargetsVC {


    @Autowired
    IPSChannelService channelService;


    @PostMapping("/ps-get-targets-summary")
    public ResponseEntity<?> getTargetsSummary() {
        ArrayList<?>targetList=channelService.getTargetSytem();
        boolean success =targetList !=null;
        //Response
        ObjectMapper objectMapper = new ObjectMapper();
        if(success){
            //Object
            ArrayNode node= objectMapper.createArrayNode();
            node.addAll((ArrayList)targetList);

            return ResponseEntity.ok(new BaseAppResponse(1,node,"Request Processed Successfully"));
        }else{

            //Response
            return ResponseEntity.ok(new BaseAppResponse(0,objectMapper.createObjectNode(),"Request could NOT be processed. Please try again later"));
        }
    }
}
