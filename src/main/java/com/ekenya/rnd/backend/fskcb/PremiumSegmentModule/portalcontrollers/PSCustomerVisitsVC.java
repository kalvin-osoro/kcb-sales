package com.ekenya.rnd.backend.fskcb.PremiumSegmentModule.portalcontrollers;

import com.ekenya.rnd.backend.fskcb.PremiumSegmentModule.models.reps.PSCustomerVisitQuestionnaireRequest;
import com.ekenya.rnd.backend.fskcb.PremiumSegmentModule.models.reps.PSCustomerVisitsRequest;
import com.ekenya.rnd.backend.fskcb.PremiumSegmentModule.services.IPSPortalService;
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
public class PSCustomerVisitsVC {

    //
    @Autowired
    IPSPortalService psService;

    @PostMapping("/ps-schedule-customer-visit")
    public ResponseEntity<?> scheduleCustomerVisit(@RequestBody PSCustomerVisitsRequest model) {
        boolean success = psService.scheduleCustomerVisit(model);
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

    @PostMapping("/ps-reschedule-customer-visit")
    public ResponseEntity<?> rescheduleCustomerVisit(@RequestBody PSCustomerVisitsRequest model) {
        boolean success = psService.rescheduleCustomerVisit(model);
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

    @PostMapping(value = "/ps-get-all-customer-visits")
    public ResponseEntity<?> getAllCustomerVisits() {
        List<?> customerVisits = psService.getAllCustomerVisits();
        boolean success = customerVisits != null;
        //Response
        ObjectMapper objectMapper = new ObjectMapper();
        if (success) {
            //Object
            ArrayNode node = objectMapper.createArrayNode();
            node.addAll((ArrayNode) objectMapper.valueToTree(customerVisits));
//          node.put("id",0);

            return ResponseEntity.ok(new BaseAppResponse(1, node, "Request Processed Successfully"));
        } else {

            //Response
            return ResponseEntity.ok(new BaseAppResponse(0, objectMapper.createArrayNode(), "Request could NOT be processed. Please try again later"));
        }
    }


    @PostMapping("/ps-get-customer-visit-questionnaire")
    public ResponseEntity<?> getCustomerVisitQuestionnaireResponses(@RequestBody PSCustomerVisitQuestionnaireRequest model) {
        List<?> customerVisits = psService.getCustomerVisitQuestionnaireResponses(model);
        boolean success = customerVisits != null;



        //Response
        ObjectMapper objectMapper = new ObjectMapper();
        if (success) {
            //Object
            ArrayNode node = objectMapper.createArrayNode();
            node.addAll((ArrayNode) objectMapper.valueToTree(customerVisits));
//          node.put("id",0);

            return ResponseEntity.ok(new BaseAppResponse(1, node, "Request Processed Successfully"));
        } else {

            //Response
            return ResponseEntity.ok(new BaseAppResponse(0, objectMapper.createArrayNode(), "Request could NOT be processed. Please try again later"));
        }
    }
}
