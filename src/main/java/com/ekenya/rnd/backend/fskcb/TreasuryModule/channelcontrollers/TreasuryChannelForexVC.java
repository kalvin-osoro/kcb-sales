package com.ekenya.rnd.backend.fskcb.TreasuryModule.channelcontrollers;

import com.ekenya.rnd.backend.fskcb.CrmAdapters.services.CRMService;
import com.ekenya.rnd.backend.fskcb.TreasuryModule.models.reqs.NegotionRateRequest;
import com.ekenya.rnd.backend.fskcb.TreasuryModule.models.reqs.TreasuryGetDSRTradeRequest;
import com.ekenya.rnd.backend.fskcb.TreasuryModule.models.reqs.TreasuryNegRequest;
import com.ekenya.rnd.backend.fskcb.TreasuryModule.models.reqs.TreasuryTradeRequest;
import com.ekenya.rnd.backend.fskcb.TreasuryModule.services.ITreasuryChannelService;
import com.ekenya.rnd.backend.fskcb.TreasuryModule.services.ITreasuryPortalService;
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
@RequestMapping(path = "/api/v1/ch")
public class TreasuryChannelForexVC {

    @Autowired
    ITreasuryChannelService channelService;
    @Autowired
    ITreasuryPortalService portalService;
    @Autowired
    CRMService crmService;

    @PostMapping("/treasury-get-forex-rates")
    public ResponseEntity<?> getForexCounterRates() {

        ArrayNode rates = crmService.getForexRates();


        //Response
        ObjectMapper objectMapper = new ObjectMapper();
        if (rates != null) {
            //Object
            //ArrayNode node = objectMapper.createArrayNode();
//          node.put("id",0);

            return ResponseEntity.ok(new BaseAppResponse(1, rates, "Request Processed Successfully"));
        } else {

            //Response
            return ResponseEntity.ok(new BaseAppResponse(0, objectMapper.createArrayNode(), "Request could NOT be processed. Please try again later"));
        }
    }

    @PostMapping("/treasury-get-forex-neg-rates")
    public ResponseEntity<?> getForexNegotiatedRates(@RequestBody NegotionRateRequest model) {

        ArrayNode forexList = crmService.loadForexCounterRates(model);

        //Response
        ObjectMapper objectMapper = new ObjectMapper();
        if (forexList != null) {
            //Object
            //ArrayNode node = objectMapper.createArrayNode();
//          node.put("id",0);

            return ResponseEntity.ok(new BaseAppResponse(1, forexList, "Request Processed Successfully"));
        } else {

            //Response
            return ResponseEntity.ok(new BaseAppResponse(0, objectMapper.createArrayNode(), "Request could NOT be processed. Please try again later"));
        }
    }

    @PostMapping("/treasury-create-trade-req")
    public ResponseEntity<?> createTradeReq(@RequestBody TreasuryTradeRequest model) {


        //TODO; INSIDE SERVICE
        boolean success = channelService.attemptCreateTradeRequest(model);

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
    public ResponseEntity<?> getAllDSRTradeReqs(@RequestBody TreasuryGetDSRTradeRequest model) {
        List<?> tradeReqs = channelService.loadAllDSRTradeReqs(model);
        boolean success = tradeReqs != null;
        //Response
        ObjectMapper objectMapper = new ObjectMapper();
        if (success) {
            //Object
            ArrayNode node = objectMapper.createArrayNode();
            node.addAll((List) tradeReqs);
//          node.put("id",0);

            return ResponseEntity.ok(new BaseAppResponse(1, node, "Request Processed Successfully"));
        } else {

            //Response
            return ResponseEntity.ok(new BaseAppResponse(0, objectMapper.createArrayNode(), "Request could NOT be processed. Please try again later"));
        }
    }

    @PostMapping("/treasury-create-neg-req")
    public ResponseEntity<?> createNegotiationReq(@RequestBody TreasuryNegRequest request) {


        //INSIDE SERVICE
        boolean success = channelService.attemptCreateNegotiationRequest(request);

        //Response
        ObjectMapper objectMapper = new ObjectMapper();
        if (success) {
            //Object
            ObjectNode node = objectMapper.createObjectNode();
            node.put("message", "successful");
//          node.put("id",0);

            return ResponseEntity.ok(new BaseAppResponse(1, node, "Request Processed Successfully"));
        } else {

            //Response
            return ResponseEntity.ok(new BaseAppResponse(0, objectMapper.createObjectNode(), "Request could NOT be processed. Please try again later"));
        }
    }

    @PostMapping(value = "/treasury-get-all-neg-reqs")
    public ResponseEntity<?> getAllDSRNegReqs(@RequestBody String dsrId) {


        //
        ArrayNode list = channelService.loadDSRNegotiationRequests();

        //Response
        ObjectMapper objectMapper = new ObjectMapper();
        if (list != null) {
            //Object
            //ArrayNode node = objectMapper.createArrayNode();
//          node.put("id",0);

            return ResponseEntity.ok(new BaseAppResponse(1, list, "Request Processed Successfully"));
        } else {

            //Response
            return ResponseEntity.ok(new BaseAppResponse(0, objectMapper.createArrayNode(), "Request could NOT be processed. Please try again later"));
        }
    }

    @PostMapping(value = "/treasury-get-all-currency-rates")
    public ResponseEntity<?> getAllCurrencyRates() {
        List<?> list = portalService.getAllCurrencyRates();
        boolean success = list != null;
        //Response
        ObjectMapper objectMapper = new ObjectMapper();
        if (list != null) {
            return ResponseEntity.ok(new BaseAppResponse(1, list, "Request Processed Successfully"));

        } else {

            //Response
            return ResponseEntity.ok(new BaseAppResponse(0, objectMapper.createArrayNode(), "Request could NOT be processed. Please try again later"));
        }

    }
}
