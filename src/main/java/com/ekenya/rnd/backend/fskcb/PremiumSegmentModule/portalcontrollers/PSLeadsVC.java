package com.ekenya.rnd.backend.fskcb.PremiumSegmentModule.portalcontrollers;

import com.ekenya.rnd.backend.fskcb.PersonalBankingModule.models.reqs.PBLeadsListRequest;
import com.ekenya.rnd.backend.fskcb.PremiumSegmentModule.models.reps.PSAddLeadRequest;
import com.ekenya.rnd.backend.fskcb.PremiumSegmentModule.models.reps.PSAssignLeadRequest;
import com.ekenya.rnd.backend.fskcb.PremiumSegmentModule.services.IPSPortalService;
import com.ekenya.rnd.backend.responses.AppResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1")
public class PSLeadsVC {
    
    @Autowired
    IPSPortalService psService;

    //Assign lead to a sales person
    @PostMapping("/ps-assign-lead")
    public ResponseEntity<?> assignLead(@RequestBody PSAddLeadRequest model) {
        boolean success = psService.assignLead(model);
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

    //List all leads
    @PostMapping(value = "/ps-get-all-leads")
    public ResponseEntity<?> getAllLeads() {
        List<?> leads = psService.getAllLeads();
        boolean success = leads != null;
        //Response
        ObjectMapper objectMapper = new ObjectMapper();
        if(success){
            //Object
            ArrayNode node = objectMapper.createArrayNode();
            node.addAll((ArrayNode) objectMapper.valueToTree(leads));
//          node.put("id",0);

            return ResponseEntity.ok(new AppResponse(1,node,"Request Processed Successfully"));
        }else{

            //Response
            return ResponseEntity.ok(new AppResponse(0,objectMapper.createArrayNode(),"Request could NOT be processed. Please try again later"));
        }
    }


}
