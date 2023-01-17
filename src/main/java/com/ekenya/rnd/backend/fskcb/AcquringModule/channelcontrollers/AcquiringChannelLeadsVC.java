package com.ekenya.rnd.backend.fskcb.AcquringModule.channelcontrollers;

import com.ekenya.rnd.backend.fskcb.AcquringModule.models.AcquiringAssignLeadRequest;
import com.ekenya.rnd.backend.fskcb.AcquringModule.models.reqs.AcquiringAddLeadRequest;
import com.ekenya.rnd.backend.fskcb.AcquringModule.services.IAcquiringChannelService;

import com.ekenya.rnd.backend.fskcb.TreasuryModule.models.reqs.TreasuryGetDSRLeads;
import com.ekenya.rnd.backend.fskcb.TreasuryModule.models.reqs.TreasuryUpdateLeadRequest;
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
    @PostMapping(value = "/acquiring-get-all-created-leads-by-dsr")
    public ResponseEntity<?> getAllDSRLeads(@RequestBody TreasuryGetDSRLeads model) {
        List<?>dsrLeads=acquiringChannelService.loadDSRLead(model);
        boolean success = dsrLeads!=null;
        //Response
        ObjectMapper objectMapper = new ObjectMapper();
        if(success){
            //Object
            ArrayNode node = objectMapper.createArrayNode();
            node.addAll((List)dsrLeads);
//          node.put("id",0);

            return ResponseEntity.ok(new BaseAppResponse(1,node,"Request Processed Successfully"));
        }else{

            //Response
            return ResponseEntity.ok(new BaseAppResponse(0,objectMapper.createArrayNode(),"Request could NOT be processed. Please try again later"));
        }
    }

    //get all assigned leads
    @PostMapping(value = "/acquiring-get-all-assigned-leads")
    public ResponseEntity<?> getAllAssignedLeads(@RequestBody TreasuryGetDSRLeads model) {
        List <?> leads = acquiringChannelService.getAllAssignedLeads(model);
        boolean success = leads  != null;
        ObjectMapper objectMapper = new ObjectMapper();
        if(success){
            //Object
            ArrayNode node = objectMapper.createArrayNode();
            node.addAll((List) leads);
//          node.put("id",0);

                return ResponseEntity.ok(new BaseAppResponse(1,node,"Request Processed Successfully"));
            }else{

                //Response
                return ResponseEntity.ok(new BaseAppResponse(0,objectMapper.createArrayNode(),"Request could NOT be processed. Please try again later"));
            }
        }

        //update lead
        @PostMapping(value = "/acquiring-update-lead")
        public ResponseEntity<?> updateLead(@RequestBody TreasuryUpdateLeadRequest model) {
            boolean success = acquiringChannelService.updateLead(model);
//            boolean success = updatedLead  != null;
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
}
