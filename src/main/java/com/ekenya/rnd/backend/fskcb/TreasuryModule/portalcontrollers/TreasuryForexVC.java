package com.ekenya.rnd.backend.fskcb.TreasuryModule.portalcontrollers;

import com.ekenya.rnd.backend.fskcb.PremiumSegmentModule.models.reps.PSCustomerVisitsRequest;
import com.ekenya.rnd.backend.fskcb.TreasuryModule.models.reqs.TreasuryApproveTradeRequest;
import com.ekenya.rnd.backend.fskcb.TreasuryModule.models.reqs.TreasuryAppveNegRequest;
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
public class TreasuryForexVC {

    @Autowired
    ITreasuryPortalService portalService;

    @PostMapping(value = "/treasury-get-all-neg-reqs")
    public ResponseEntity<?> getAllNegotiationReqs() {


        //
        ArrayNode resp = portalService.loadNegotiationRequests();

        //Response
        ObjectMapper objectMapper = new ObjectMapper();
        if (resp != null) {
            //Object
            //ArrayNode node = objectMapper.createArrayNode();
//          node.put("id",0);

            return ResponseEntity.ok(new AppResponse(1, resp, "Request Processed Successfully"));
        } else {

            //Response
            return ResponseEntity.ok(new AppResponse(0, objectMapper.createArrayNode(), "Request could NOT be processed. Please try again later"));
        }
    }


    @PostMapping("/treasury-approve-neg-req")
    public ResponseEntity<?> approveNegotiationReq(@RequestBody TreasuryAppveNegRequest assetManagementRequest) {


        //
        boolean success = portalService.approveNegotiationRequest();

        //Response
        ObjectMapper objectMapper = new ObjectMapper();
        if (success) {
            //Object
            ObjectNode node = objectMapper.createObjectNode();
//          node.put("id",0);

            return ResponseEntity.ok(new AppResponse(1, node, "Request Processed Successfully"));
        } else {

            //Response
            return ResponseEntity.ok(new AppResponse(0, objectMapper.createObjectNode(), "Request could NOT be processed. Please try again later"));
        }
    }

    @PostMapping(value = "/treasury-get-all-trade-reqs")
    public ResponseEntity<?> getAllTradeReqs() {


        //
        ArrayNode resp = portalService.loadTradeRequests();

        //Response
        ObjectMapper objectMapper = new ObjectMapper();
        if (resp != null) {
            //Object
            //ArrayNode node = objectMapper.createArrayNode();
//          node.put("id",0);

            return ResponseEntity.ok(new AppResponse(1, resp, "Request Processed Successfully"));
        } else {

            //Response
            return ResponseEntity.ok(new AppResponse(0, objectMapper.createArrayNode(), "Request could NOT be processed. Please try again later"));
        }
    }


    @PostMapping("/treasury-approve-trade-req")
    public ResponseEntity<?> approveTradeReq(@RequestBody TreasuryApproveTradeRequest assetManagementRequest) {


        //
        boolean success = false;//acquiringService..(model);

        //Response
        ObjectMapper objectMapper = new ObjectMapper();
        if (success) {
            //Object
            ObjectNode node = objectMapper.createObjectNode();
//          node.put("id",0);

            return ResponseEntity.ok(new AppResponse(1, node, "Request Processed Successfully"));
        } else {

            //Response
            return ResponseEntity.ok(new AppResponse(0, objectMapper.createObjectNode(), "Request could NOT be processed. Please try again later"));
        }
    }
}
