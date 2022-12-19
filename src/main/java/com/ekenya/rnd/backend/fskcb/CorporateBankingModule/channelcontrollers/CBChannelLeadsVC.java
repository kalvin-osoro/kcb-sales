package com.ekenya.rnd.backend.fskcb.CorporateBankingModule.channelcontrollers;

import com.ekenya.rnd.backend.fskcb.CorporateBankingModule.models.CBGetLeadsByDsrIdRequest;
import com.ekenya.rnd.backend.fskcb.CorporateBankingModule.models.reqs.CBAddLeadRequest;
import com.ekenya.rnd.backend.fskcb.CorporateBankingModule.services.ICBChannelService;
import com.ekenya.rnd.backend.fskcb.TreasuryModule.models.reqs.TreasuryAddLeadRequest;
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
public class CBChannelLeadsVC {
    @Autowired
    private ICBChannelService channelService;

    //Create new lead
    @PostMapping("/cb-create-lead")
    public ResponseEntity<?> createLead(@RequestBody TreasuryAddLeadRequest model) {

        //TODO;
        boolean success = channelService.attemptCreateLead(model);

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

    @PostMapping(value = "/cb-get-all-created-leads-by-dsr")
    public ResponseEntity<?> getAllDSRLeads(@RequestBody TreasuryGetDSRLeads model) {
        List<?>dsrLeads=channelService.loadDSRLead(model);
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

    //get all leads assigned to a dsrId
    @PostMapping(value = "/cb-get-all-assigned-leads-by-dsr")
    public ResponseEntity<?> getAllAssignedDSRLeads(@RequestBody TreasuryGetDSRLeads model) {
        List<?>dsrLeads=channelService.loadAssignedDSRLead(model);
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
    //update lead
    @PostMapping(value = "/cb-update-lead")
    public ResponseEntity<?> updateLead(@RequestBody TreasuryUpdateLeadRequest model) {
        boolean success = channelService.attemptUpdateLead(model);
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
