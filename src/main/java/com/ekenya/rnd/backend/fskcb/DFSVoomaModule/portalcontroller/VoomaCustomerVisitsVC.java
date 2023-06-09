package com.ekenya.rnd.backend.fskcb.DFSVoomaModule.portalcontroller;

import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.models.reqs.VoomaCustomerVisitQuestionnaireRequest;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.models.reqs.VoomaCustomerVisitsByIdRequest;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.models.reqs.VoomaCustomerVisitsRequest;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.services.IVoomaPortalService;
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
public class VoomaCustomerVisitsVC {

    @Autowired
    IVoomaPortalService voomaService;

    @PostMapping("/vooma-schedule-customer-visit")
    public ResponseEntity<?> scheduleCustomerVisit(@RequestBody VoomaCustomerVisitsRequest model) {
        boolean success = voomaService.scheduleCustomerVisit(model);
        //Response
        ObjectMapper objectMapper = new ObjectMapper();
        if (success) {
            //Object
            ObjectNode node = objectMapper.createObjectNode();
            node.put("success", success);
//          node.put("id",0);

            return ResponseEntity.ok(new BaseAppResponse(1, node, "Request Processed Successfully"));
        } else {

            //Response
            return ResponseEntity.ok(new BaseAppResponse(0, objectMapper.createObjectNode(), "Request could NOT be processed. Please try again later"));
        }
    }

    @PostMapping("/vooma-reschedule-customer-visit")
    public ResponseEntity<?> rescheduleCustomerVisit(@RequestBody VoomaCustomerVisitsRequest voomaCustomerVisitsRequest) {


        //TODO; INSIDE SERVICE
        boolean success = voomaService.reScheduleCustomerVisit(voomaCustomerVisitsRequest);

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

    @PostMapping(value = "/vooma-get-all-customer-visits")
    public ResponseEntity<?> getAllCustomerVisits() {
        List<?> customerVisits = voomaService.getAllCustomerVisits();
        boolean success = customerVisits != null;
        //Response
        ObjectMapper objectMapper = new ObjectMapper();
        if (success) {
            //Object
            ArrayNode node = objectMapper.createArrayNode();
            node.addAll((List) customerVisits);
//          node.put("id",0);

            return ResponseEntity.ok(new BaseAppResponse(1, node, "Request Processed Successfully"));
        } else {

            //Response
            return ResponseEntity.ok(new BaseAppResponse(0, objectMapper.createArrayNode(), "Request could NOT be processed. Please try again later"));
        }
    }


    @PostMapping("/vooma-get-customer-visit-questionnaire-response")
    public ResponseEntity<?> getCustomerVisitQuestionnaireResponses(@RequestBody VoomaCustomerVisitQuestionnaireRequest voomaCustomerVisitQuestionnaireRequest) {

        List<?> customerVisitQuestionnaireResponses = voomaService.getCustomerVisitQuestionnaireResponses(voomaCustomerVisitQuestionnaireRequest);
        boolean success = customerVisitQuestionnaireResponses != null;
        //Response
        ObjectMapper objectMapper = new ObjectMapper();
        if (success) {
            //Object
            ArrayNode node = objectMapper.createArrayNode();
            node.addAll((ArrayNode) objectMapper.valueToTree(customerVisitQuestionnaireResponses));
//          node.put("id",0);

            return ResponseEntity.ok(new BaseAppResponse(1, node, "Request Processed Successfully"));
        } else {

            //Response
            return ResponseEntity.ok(new BaseAppResponse(0, objectMapper.createArrayNode(), "Request could NOT be processed. Please try again later"));
        }
    }
    //region,branch,dsrName,customerName,location,visitDate,latitude,longitude

    //get customer visits by id
    @PostMapping("/vooma-get-customer-visit-by-id")
    public ResponseEntity<?> getCustomerVisitById(@RequestBody VoomaCustomerVisitsByIdRequest model) {

        Object customerVisit = voomaService.getCustomerVisitById(model);
        boolean success = customerVisit != null;
        //Response
        ObjectMapper objectMapper = new ObjectMapper();
        if (success) {
            //Object
            ObjectNode node = objectMapper.createObjectNode();
            node.putPOJO("customerVisit", customerVisit);
            return ResponseEntity.ok(new BaseAppResponse(1, node, "Request Processed Successfully"));
        } else {

            //Response
            return ResponseEntity.ok(new BaseAppResponse(0, objectMapper.createObjectNode(), "Request could NOT be processed. Please try again later"));
        }


    }
}
