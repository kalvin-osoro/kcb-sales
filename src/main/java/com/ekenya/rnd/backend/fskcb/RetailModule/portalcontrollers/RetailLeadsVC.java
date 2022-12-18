package com.ekenya.rnd.backend.fskcb.RetailModule.portalcontrollers;//package ekenya.co.ke.frp_kcb.RetailModule.controllers;

import com.ekenya.rnd.backend.fskcb.RetailModule.models.reqs.RetailAssignLeadRequest;
import com.ekenya.rnd.backend.fskcb.RetailModule.models.reqs.RetailLeadsListRequest;
import com.ekenya.rnd.backend.fskcb.RetailModule.services.IRetailPortalService;
import com.ekenya.rnd.backend.fskcb.TreasuryModule.models.reqs.TreasuryAssignLeadRequest;
import com.ekenya.rnd.backend.responses.BaseAppResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("/retail-lead")
@RequestMapping(path = "/api/v1")
public class RetailLeadsVC {
    
    @Autowired
    IRetailPortalService retailService;

    //Assign lead to a sales person
    @PostMapping("/retail-micro-assign-lead")
    public ResponseEntity<?> assignLead(@RequestBody TreasuryAssignLeadRequest model) {


        //INSIDE SERVICE
        boolean success = retailService.assignLead(model);

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
    @PostMapping(value = "/retail-micro-get-all-leads")
    public ResponseEntity<?> getAllLeads() {
        List<?>list=retailService.getAllLeads();
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
