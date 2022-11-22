package com.ekenya.rnd.backend.fskcb.TreasuryModule.portalcontrollers;

import com.ekenya.rnd.backend.fskcb.TreasuryModule.models.reqs.TreasuryApproveTradeRequest;
import com.ekenya.rnd.backend.fskcb.TreasuryModule.models.reqs.TreasuryAppveNegRequest;
import com.ekenya.rnd.backend.fskcb.TreasuryModule.services.ITreasuryPortalService;
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
public class TreasuryForexVC {

    @Autowired
    ITreasuryPortalService portalService;

    @PostMapping(value = "/treasury-get-all-neg-reqs")
    public ResponseEntity<?> getAllNegotiationReqs() {
        List<?>list = portalService.getAllNegotiationReqs();
        boolean success = list != null;

        ObjectMapper objectMapper = new ObjectMapper();
        if (success) {
            //Object
            ArrayNode node = objectMapper.createArrayNode();
            node.addAll((ArrayNode) objectMapper.valueToTree(list));
//          node.put("id",0);

            return ResponseEntity.ok(new BaseAppResponse(1, node, "Request Processed Successfully"));
        } else {

            //Response
            return ResponseEntity.ok(new BaseAppResponse(0, objectMapper.createArrayNode(), "Request could NOT be processed. Please try again later"));
        }
    }


    @PostMapping("/treasury-approve-neg-req")
    public ResponseEntity<?> approveNegotiationReq(@RequestBody TreasuryAppveNegRequest model) {


        //
        boolean success = portalService.approveNegotiationRequest(model);

        //Response
        ObjectMapper objectMapper = new ObjectMapper();
        if (success) {
            //Object
            ObjectNode node = objectMapper.createObjectNode();
//          node.put("id",0);

            return ResponseEntity.ok(new BaseAppResponse(1, node, "Request Processed Successfully"));
        } else {

            //Response
            return ResponseEntity.ok(new BaseAppResponse(0, objectMapper.createObjectNode(), "Request could NOT be processed. Please try again later"));
        }
    }

    @PostMapping(value = "/treasury-get-all-trade-reqs")
    public ResponseEntity<?> getAllTradeReqs() {
        List<?>list = portalService.getAllTradeReqs();
        boolean success = list != null;
        //Response
        ObjectMapper objectMapper = new ObjectMapper();
        if (success) {
            ArrayNode node = objectMapper.createArrayNode();
            node.addAll((ArrayNode) objectMapper.valueToTree(list));
            //Object

            return ResponseEntity.ok(new BaseAppResponse(1, node, "Request Processed Successfully"));
        } else {

            //Response
            return ResponseEntity.ok(new BaseAppResponse(0, objectMapper.createArrayNode(), "Request could NOT be processed. Please try again later"));
        }
    }


    @PostMapping("/treasury-approve-trade-req")
    public ResponseEntity<?> approveTradeReq(@RequestBody TreasuryApproveTradeRequest model) {
        boolean success = portalService.approveTradeRequest(model);
        //Response
        ObjectMapper objectMapper = new ObjectMapper();
        if (success) {
            //Object
            ObjectNode node = objectMapper.createObjectNode();
//          node.put("id",0);

            return ResponseEntity.ok(new BaseAppResponse(1, node, "Request Processed Successfully"));
        } else {

            //Response
            return ResponseEntity.ok(new BaseAppResponse(0, objectMapper.createObjectNode(), "Request could NOT be processed. Please try again later"));
        }
    }
}
