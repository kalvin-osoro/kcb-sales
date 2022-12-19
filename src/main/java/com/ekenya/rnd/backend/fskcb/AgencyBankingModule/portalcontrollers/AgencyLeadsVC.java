package com.ekenya.rnd.backend.fskcb.AgencyBankingModule.portalcontrollers;

import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.models.reqs.AgencyAssignLeadRequest;
import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.services.IAgencyPortalService;
import com.ekenya.rnd.backend.fskcb.TreasuryModule.models.reqs.TreasuryAssignLeadRequest;
import com.ekenya.rnd.backend.responses.BaseAppResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1")
@RequiredArgsConstructor
public class AgencyLeadsVC {
    public final IAgencyPortalService agencyService;


    //Assign lead to a sales person
    @PostMapping("/agency-assign-lead")
    public ResponseEntity<?> assignLead(@RequestBody TreasuryAssignLeadRequest model) {


        //INSIDE SERVICE
        boolean success = agencyService.assignLead(model);

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

    //List all leads
    @PostMapping(value = "/agency-get-all-leads")
    public ResponseEntity<?> getAllLeads() {
        List<?>list=agencyService.getAllLeads();
        boolean success = list != null;
        //Response
        ObjectMapper objectMapper = new ObjectMapper();
        if(success){
            //Object
            ArrayNode node = objectMapper.createArrayNode();
            node.addAll((ArrayNode) objectMapper.valueToTree(list));

//          node.put("id",0);

            return ResponseEntity.ok(new BaseAppResponse(1,node,"Request Processed Successfully"));
        }else{

            //Response
            return ResponseEntity.ok(new BaseAppResponse(0,objectMapper.createArrayNode(),"Request could NOT be processed. Please try again later"));
        }
    }


}
