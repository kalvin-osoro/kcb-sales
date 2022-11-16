package com.ekenya.rnd.backend.fskcb.TreasuryModule.portalcontrollers;

import com.ekenya.rnd.backend.fskcb.TreasuryModule.models.reqs.TreasurySummaryRequest;
import com.ekenya.rnd.backend.fskcb.TreasuryModule.services.ITreasuryPortalService;
import com.ekenya.rnd.backend.responses.AppResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1")
public class TreasuryDashboardsVC {

    @Autowired
    ITreasuryPortalService treasuryService;

    @PostMapping(value = "/treasury-targets-summary")
    public ResponseEntity<?> getTargetsSummary(@RequestBody TreasurySummaryRequest filters) {


        ArrayNode resp = treasuryService.loadTargetsSummary(filters);

        //Response
        ObjectMapper objectMapper = new ObjectMapper();
        if(resp != null){
            //Object
            //ObjectNode node = objectMapper.createObjectNode();
//          node.put("id",0);

            return ResponseEntity.ok(new AppResponse(1,resp,"Request Processed Successfully"));
        }else{

            //Response
            return ResponseEntity.ok(new AppResponse(0,objectMapper.createObjectNode(),"Request could NOT be processed. Please try again later"));
        }
    }

    @PostMapping(value = "/treasury-leads-summary")
    public ResponseEntity<?> getLeadsSummary(@RequestBody TreasurySummaryRequest filters) {


        //
        ObjectNode resp = treasuryService.loadLeadsSummary(filters);

        //Response
        ObjectMapper objectMapper = new ObjectMapper();
        if(resp != null){
            //Object
            //ObjectNode node = objectMapper.createObjectNode();
//          node.put("id",0);

            return ResponseEntity.ok(new AppResponse(1,resp,"Request Processed Successfully"));
        }else{

            //Response
            return ResponseEntity.ok(new AppResponse(0,objectMapper.createObjectNode(),"Request could NOT be processed. Please try again later"));
        }
    }


    @PostMapping(value = "/treasury-requests-summary")
    public ResponseEntity<?> getRequestsSummary(@RequestBody TreasurySummaryRequest filters) {

        //
        ObjectNode resp = treasuryService.loadRequestsSummary(filters);

        //Response
        ObjectMapper objectMapper = new ObjectMapper();
        if(resp != null){
            //Object
            //ObjectNode node = objectMapper.createObjectNode();
//          node.put("id",0);

            return ResponseEntity.ok(new AppResponse(1,resp,"Request Processed Successfully"));
        }else{

            //Response
            return ResponseEntity.ok(new AppResponse(0,objectMapper.createObjectNode(),"Request could NOT be processed. Please try again later"));
        }
    }
}
