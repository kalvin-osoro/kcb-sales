package com.ekenya.rnd.backend.fskcb.RetailModule.portalcontrollers;

import com.ekenya.rnd.backend.fskcb.RetailModule.models.reqs.RetailAddTargetRequest;
import com.ekenya.rnd.backend.fskcb.RetailModule.models.reqs.RetailDSRsInTargetRequest;
import com.ekenya.rnd.backend.fskcb.RetailModule.services.IRetailPortalService;
import com.ekenya.rnd.backend.responses.AppResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1")
public class RetailTargetsVC {

    @Autowired
    IRetailPortalService retailService;

    @PostMapping("/retail-create-target")
    public ResponseEntity<?> createRetailAsset(@RequestBody RetailAddTargetRequest assetManagementRequest) {


        //

        //TODO;
        boolean success = false;//acquiringService..(model);

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

    @RequestMapping(value = "/retail-get-all-targets", method = RequestMethod.GET)
    public ResponseEntity<?> getAllTargets() {

        //

        //TODO;
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


    @RequestMapping(value = "/retail-get-agents-in-target", method = RequestMethod.GET)
    public ResponseEntity<?> getRetailAgentsInTarget(RetailDSRsInTargetRequest model) {

        //

        //TODO;
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



    @RequestMapping(value = "/retail-sync-crm-targets", method = RequestMethod.GET)
    public ResponseEntity<?> getRetailSyncTargetsWithCRM() {

        //

        //TODO;
        boolean success = false;//acquiringService..(model);

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
}
