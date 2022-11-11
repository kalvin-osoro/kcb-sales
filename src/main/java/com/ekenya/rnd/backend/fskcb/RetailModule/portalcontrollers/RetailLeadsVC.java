package com.ekenya.rnd.backend.fskcb.RetailModule.portalcontrollers;//package ekenya.co.ke.frp_kcb.RetailModule.controllers;

import com.ekenya.rnd.backend.fskcb.RetailModule.models.RetailAssignLeadRequest;
import com.ekenya.rnd.backend.fskcb.RetailModule.models.RetailLeadRequest;
import com.ekenya.rnd.backend.fskcb.RetailModule.models.RetailLeadsListRequest;
import com.ekenya.rnd.backend.fskcb.RetailModule.services.IRetailService;
import com.ekenya.rnd.backend.responses.AppResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("/retail-lead")
public class RetailLeadsVC {
    
    @Autowired
    IRetailService retailService;

    //Assign lead to a sales person
    @PostMapping("/retail-assign-lead")
    public ResponseEntity<?> createLead(@RequestBody RetailAssignLeadRequest model) {


        //TODO; INSIDE SERVICE
        boolean success = false;//acquiringService.assigneLeadtoDSR(model);

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
    @RequestMapping(value = "/retail-get-all-leads", method = RequestMethod.GET)
    public ResponseEntity<?> getAllLeads(@RequestBody RetailLeadsListRequest filters) {

        //
        //TODO; INSIDE SERVICE
        boolean success = false;//acquiringService..(model);

        //Response
        ObjectMapper objectMapper = new ObjectMapper();
        if(success){
            //Object
            ArrayNode node = objectMapper.createArrayNode();
//          node.put("id",0);

            return ResponseEntity.ok(new AppResponse(1,node,"Request Processed Successfully"));
        }else{

            //Response
            return ResponseEntity.ok(new AppResponse(0,objectMapper.createArrayNode(),"Request could NOT be processed. Please try again later"));
        }
    }


}
