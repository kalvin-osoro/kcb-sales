package com.ekenya.rnd.backend.fskcb.AcquringModule.channelcontrollers;

import com.ekenya.rnd.backend.fskcb.AcquringModule.models.AcquiringAssignLeadRequest;
import com.ekenya.rnd.backend.fskcb.AcquringModule.models.reqs.AcquiringAddLeadRequest;
import com.ekenya.rnd.backend.fskcb.AcquringModule.services.IAcquiringChannelService;

import com.ekenya.rnd.backend.responses.BaseAppResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(path = "/api/v1/ch")
public class AcquiringChannelLeadsVC {

    @Autowired
    IAcquiringChannelService acquiringChannelService;

    //Create new lead
    @PostMapping("/acquiring-create-lead")
    public ResponseEntity<?> createAcquiringLead(@RequestBody AcquiringAddLeadRequest model) {
        boolean success = acquiringChannelService.createLead(model);
        //Response
        ObjectMapper objectMapper = new ObjectMapper();
        if(success){
            //Object
            ObjectNode node = objectMapper.createObjectNode();


            return ResponseEntity.ok(new BaseAppResponse(1,node,"Request Processed Successfully"));
        }else{

            //Response
            return ResponseEntity.ok(new BaseAppResponse(0,objectMapper.createObjectNode(),"Request could NOT be processed. Please try again later"));
        }
    }
    @PostMapping(value = "/acquiring-get-all-leads")
    public ResponseEntity<?> getAllLeads() {
        List <?> leads = acquiringChannelService.getAllLeads();
        boolean success = leads  != null;
        ObjectMapper objectMapper = new ObjectMapper();
        if(success){
            //Object
            ArrayNode node = objectMapper.createArrayNode();
//          node.put("id",0);

            return ResponseEntity.ok(new BaseAppResponse(1,node,"Request Processed Successfully"));
        }else{

            //Response
            return ResponseEntity.ok(new BaseAppResponse(0,objectMapper.createArrayNode(),"Request could NOT be processed. Please try again later"));
        }
    }

    //get all assigned leads
    @PostMapping(value = "/acquiring-get-all-assigned-leads")
    public ResponseEntity<?> getAllAssignedLeads() {
        List <?> leads = acquiringChannelService.getAllAssignedLeads();
        boolean success = leads  != null;
        ObjectMapper objectMapper = new ObjectMapper();
        if(success){
            //Object
            ArrayNode node = objectMapper.createArrayNode();
            node.addAll((ArrayNode) leads);
//          node.put("id",0);

                return ResponseEntity.ok(new BaseAppResponse(1,node,"Request Processed Successfully"));
            }else{

                //Response
                return ResponseEntity.ok(new BaseAppResponse(0,objectMapper.createArrayNode(),"Request could NOT be processed. Please try again later"));
            }
        }

        //update lead
        @PostMapping(value = "/acquiring-update-lead")
        public ResponseEntity<?> updateLead(@RequestBody AcquiringAddLeadRequest model) {
            Object updatedLead = acquiringChannelService.updateLead(model);
            boolean success = updatedLead  != null;
            ObjectMapper objectMapper = new ObjectMapper();
            if(success){
                //Object
                ObjectNode node = objectMapper.createObjectNode();
                node.putArray("lead").add((ObjectNode) updatedLead);

                return ResponseEntity.ok(new BaseAppResponse(1,node,"Request Processed Successfully"));
            }else{

                //Response
                return ResponseEntity.ok(new BaseAppResponse(0,objectMapper.createObjectNode(),"Request could NOT be processed. Please try again later"));
            }
        }
}
