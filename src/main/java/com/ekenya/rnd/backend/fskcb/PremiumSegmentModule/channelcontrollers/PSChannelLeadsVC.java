package com.ekenya.rnd.backend.fskcb.PremiumSegmentModule.channelcontrollers;

import com.ekenya.rnd.backend.fskcb.PremiumSegmentModule.models.reps.PSAddLeadRequest;
import com.ekenya.rnd.backend.fskcb.PremiumSegmentModule.models.reps.PSGetDSRLeadsRequest;
import com.ekenya.rnd.backend.fskcb.PremiumSegmentModule.services.IPSChannelService;
import com.ekenya.rnd.backend.responses.BaseAppResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/ch")
public class PSChannelLeadsVC {

    @Autowired
    IPSChannelService channelService;

    //Create new lead
    @PostMapping("/ps-create-lead")
    public ResponseEntity<?> createLead(@RequestBody PSAddLeadRequest model) {
        boolean success =channelService.createLead(model);



        //TODO;

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
    @PostMapping(value = "/ps-get-all-leads")
    public ResponseEntity<?> getAllLeads(@RequestBody PSGetDSRLeadsRequest model) {
        List<?> leads = channelService.getAllLeads(model);
        boolean success = leads != null;
        //Response
        ObjectMapper objectMapper = new ObjectMapper();
        if(success){
            //Object
            ArrayNode node = objectMapper.createArrayNode();
            node.addAll((ArrayNode) objectMapper.valueToTree(leads));
//          node.put("id",0);

            return ResponseEntity.ok(new BaseAppResponse(1,node,"Request Processed Successfully"));
        }else{

            //Response
            return ResponseEntity.ok(new BaseAppResponse(0,objectMapper.createArrayNode(),"Request could NOT be processed. Please try again later"));
        }
    }
}
