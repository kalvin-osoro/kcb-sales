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

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1")
public class TreasuryDashboardsVC {

    @Autowired
    ITreasuryPortalService treasuryService;

    @PostMapping(value = "/treasury-targets-summary")
    public ResponseEntity<?> getTargetsSummary(@RequestBody TreasurySummaryRequest filters) {
        List<?> list = treasuryService.getTargetsSummary(filters);
        boolean success = list != null;


        //Response
        ObjectMapper objectMapper = new ObjectMapper();
        if(success){
            //Object
            ArrayNode node = objectMapper.createArrayNode();
            node.addAll((ArrayNode) objectMapper.valueToTree(list));
            return ResponseEntity.ok(new AppResponse(1,node,"Request Processed Successfully"));
        }else{

            //Response
            return ResponseEntity.ok(new AppResponse(0,objectMapper.createObjectNode(),"Request could NOT be processed. Please try again later"));
        }
    }

    @PostMapping(value = "/treasury-leads-summary")
    public ResponseEntity<?> getLeadsSummary(@RequestBody TreasurySummaryRequest filters) {
        List<?> list = treasuryService.getLeadsSummary(filters);
        boolean success = list != null;
        //Response
        ObjectMapper objectMapper = new ObjectMapper();
        if(success){
            //Object
            ArrayNode node = objectMapper.createArrayNode();
            node.addAll((ArrayNode) objectMapper.valueToTree(list));
            return ResponseEntity.ok(new AppResponse(1,node,"Request Processed Successfully"));
        }else{

            //Response
            return ResponseEntity.ok(new AppResponse(0,objectMapper.createObjectNode(),"Request could NOT be processed. Please try again later"));
        }
    }


    @PostMapping(value = "/treasury-requests-summary")
    public ResponseEntity<?> getRequestsSummary(@RequestBody TreasurySummaryRequest filters) {

        //
        ObjectNode resp = treasuryService.loadRequestsSummary(filters);
        boolean success = resp != null;

        //Response
        ObjectMapper objectMapper = new ObjectMapper();
        if(resp != null){
            //Object
            ObjectNode node = objectMapper.createObjectNode();
            node.setAll(resp);
//          node.put("id",0);

            return ResponseEntity.ok(new AppResponse(1,resp,"Request Processed Successfully"));
        }else{

            //Response
            return ResponseEntity.ok(new AppResponse(0,objectMapper.createObjectNode(),"Request could NOT be processed. Please try again later"));
        }
    }
}
