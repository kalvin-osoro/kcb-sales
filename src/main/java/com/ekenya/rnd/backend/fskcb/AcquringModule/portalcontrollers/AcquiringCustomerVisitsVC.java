package com.ekenya.rnd.backend.fskcb.AcquringModule.portalcontrollers;

import com.ekenya.rnd.backend.fskcb.AcquringModule.models.CustomerVisitQuestionnaireRequest;
import com.ekenya.rnd.backend.fskcb.AcquringModule.models.CustomerVisitsRequest;
import com.ekenya.rnd.backend.fskcb.AcquringModule.services.IAcquiringPortalService;
import com.ekenya.rnd.backend.responses.AppResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AcquiringCustomerVisitsVC {

    @Autowired
    IAcquiringPortalService acquiringService;
    @PostMapping("/acquiring-schedule-customer-visit")
    public ResponseEntity<?> scheduleCustomerVisit(@RequestBody CustomerVisitsRequest customerVisitsRequest) {


        boolean success = acquiringService.scheduleCustomerVisit(customerVisitsRequest);
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

    @PutMapping("/acquiring-reschedule-customer-visit/{id}")
    public ResponseEntity<?> reScheduleCustomerVisit(@PathVariable Long id, @RequestBody CustomerVisitsRequest customerVisitsRequest) {
        boolean success = acquiringService.reScheduleCustomerVisit(customerVisitsRequest, id);
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

    @RequestMapping(value = "/acquiring-get-all-customer-visits", method = RequestMethod.GET)
    public ResponseEntity<?> getAllCustomerVisits() {
        List<?> acquiringCustomerVisit = acquiringService.loadCustomerVisits();
        boolean success = acquiringCustomerVisit == null;



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

    //get questionnaire responses by visit id and questionnaire id
    @RequestMapping(value = "/acquiring-get-customer-visit-questionnaire-responses/{visitId}/{questionnaireId}", method = RequestMethod.GET)
    public ResponseEntity<?> getCustomerVisitQuestionnaireResponses(@PathVariable Long visitId, @PathVariable Long questionnaireId) {
        List<?> acquiringCustomerVisit = acquiringService.getCustomerVisitQuestionnaireResponses(visitId, questionnaireId);
        boolean success = acquiringCustomerVisit == null;



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
}
