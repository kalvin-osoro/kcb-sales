package com.ekenya.rnd.backend.fskcb.AgencyBankingModule.portalcontrollers;

import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.models.reqs.AgencyAddTargetRequest;
import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.models.reqs.AgencyDSRsInTargetRequest;
import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.services.IAgencyPortalService;
import com.ekenya.rnd.backend.responses.BaseAppResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1")
public class AgencyTargetsVC {

    @Autowired
    IAgencyPortalService agencyService;

    @PostMapping("/agency-create-target")
    public ResponseEntity<?> createAgencyTarget(@RequestBody AgencyAddTargetRequest agencyAddTargetRequest) {

        boolean success = agencyService.createAgencyTarget(agencyAddTargetRequest);
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

    @PostMapping(value = "/agency-get-all-targets")
    public ResponseEntity<?> getAllTargets() {
        List<?> agencyTargetsResponse = agencyService.loadAgencyTargets();
        boolean success = agencyTargetsResponse != null;// agencyTargetsResponse.size() > 0;


        //Response
        ObjectMapper objectMapper = new ObjectMapper();
        if(success){
            //Object
            ArrayNode node = objectMapper.createArrayNode();
            node.addAll((ArrayNode) objectMapper.valueToTree(agencyTargetsResponse));
//          node.put("id",0);

            return ResponseEntity.ok(new BaseAppResponse(1,node,"Request Processed Successfully"));
        }else{

            //Response
            return ResponseEntity.ok(new BaseAppResponse(0,objectMapper.createArrayNode(),"Request could NOT be processed. Please try again later"));
        }
    }


    @PostMapping("/agency-get-dsrs-in-target")
    public ResponseEntity<?> getDSRsInTarget(@RequestBody AgencyDSRsInTargetRequest agencyDSRsInTargetRequest) {
       //TODO;
        boolean success = false;//acquiringService..(model);

        //Response
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



    @PostMapping(value = "/agency-sync-crm-targets")
    public ResponseEntity<?> getAgencySyncTargetsWithCRM() {

        //

        //TODO;
        boolean success = false;//acquiringService..(model);

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
