package com.ekenya.rnd.backend.fskcb.CorporateBankingModule.portalcontrollers;

import com.ekenya.rnd.backend.fskcb.CorporateBankingModule.models.reqs.CBAddOpportunityRequest;
import com.ekenya.rnd.backend.fskcb.CorporateBankingModule.models.reqs.CBGetOppByIdRequest;
import com.ekenya.rnd.backend.fskcb.CorporateBankingModule.services.ICBPortalService;
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
@RequestMapping("/api/v1")
public class CBOpportunityVC {
    @Autowired
    private ICBPortalService cbService;
    @PostMapping("/cb-add-opportunity")
    ResponseEntity<?> addOpportunity(@RequestBody CBAddOpportunityRequest model){
        boolean success = cbService.addOpportunity(model);
        ObjectMapper objectMapper = new ObjectMapper();
        if(success){
            ObjectNode node = objectMapper.createObjectNode();
            node.put("message", "Opportunity added successfully");
            return ResponseEntity.ok(new BaseAppResponse(1,objectMapper.createObjectNode(),"Request Processed Successfully"));
        }else{
            return ResponseEntity.ok(new BaseAppResponse(0,objectMapper.createObjectNode(),"Request could NOT be processed. Please try again later"));

        }
    }
    //get all opportunities
    @PostMapping("/cb-get-all-opportunities")
    ResponseEntity<?> getAllOpportunities(){
        List<?> response = cbService.getAllOpportunities();
        boolean success = response != null;
        ObjectMapper objectMapper = new ObjectMapper();
        if(success){
            ArrayNode node = objectMapper.createArrayNode();
            node.addAll((List)response);
            return ResponseEntity.ok(new BaseAppResponse(1,node,"Request Processed Successfully"));
        }else{
            return ResponseEntity.ok(new BaseAppResponse(0,objectMapper.createObjectNode(),"Request could NOT be processed. Please try again later"));

        }
    }
    //get opportunity by id
    @PostMapping("/cb-get-opportunity-by-id")
    ResponseEntity<?> getOpportunityById(@RequestBody CBGetOppByIdRequest model){
        Object response = cbService.getOpportunityById(model);
        boolean success = response != null;
        ObjectMapper objectMapper = new ObjectMapper();
        if(success){
            ObjectNode node = objectMapper.createObjectNode();
            node.setAll((ObjectNode)response);
            return ResponseEntity.ok(new BaseAppResponse(1,node,"Request Processed Successfully"));
        }else{
            return ResponseEntity.ok(new BaseAppResponse(0,objectMapper.createObjectNode(),"Request could NOT be processed. Please try again later"));

        }
    }
}
