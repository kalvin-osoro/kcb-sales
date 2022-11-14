package com.ekenya.rnd.backend.fskcb.TreasuryModule.portalcontrollers;

import com.ekenya.rnd.backend.fskcb.PremiumSegmentModule.models.PSCustomerVisitsRequest;
import com.ekenya.rnd.backend.responses.AppResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1")
public class TreasuryNegRequestsVC {


    @RequestMapping(value = "/treasury-get-all-neg-reqs", method = RequestMethod.GET)
    public ResponseEntity<?> getAllCustomerVisits() {


        //TODO; INSIDE SERVICE
        boolean success = false;//acquiringService..(model);

        //Response
        ObjectMapper objectMapper = new ObjectMapper();
        if (success) {
            //Object
            ArrayNode node = objectMapper.createArrayNode();
//          node.put("id",0);

            return ResponseEntity.ok(new AppResponse(1, node, "Request Processed Successfully"));
        } else {

            //Response
            return ResponseEntity.ok(new AppResponse(0, objectMapper.createArrayNode(), "Request could NOT be processed. Please try again later"));
        }
    }


    @PostMapping("/treasury-approve-neg-req")
    public ResponseEntity<?> rescheduleCustomerVisit(@RequestBody PSCustomerVisitsRequest assetManagementRequest) {


        //TODO; INSIDE SERVICE
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
