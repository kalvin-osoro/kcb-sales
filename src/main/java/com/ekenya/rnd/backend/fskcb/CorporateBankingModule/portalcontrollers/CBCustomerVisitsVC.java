package com.ekenya.rnd.backend.fskcb.CorporateBankingModule.portalcontrollers;

import com.ekenya.rnd.backend.fskcb.CorporateBankingModule.models.reqs.CBCustomerVisitQuestionnaireRequest;
import com.ekenya.rnd.backend.fskcb.CorporateBankingModule.models.reqs.CBCustomerVisitsRequest;
import com.ekenya.rnd.backend.fskcb.CorporateBankingModule.services.ICBPortalService;
import com.ekenya.rnd.backend.responses.AppResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1")
public class CBCustomerVisitsVC {
    //
   private final ICBPortalService cbService;

    public CBCustomerVisitsVC(ICBPortalService service) {
        this.cbService = service;
    }

    @PostMapping("/cb-schedule-customer-visit")
    public ResponseEntity<?> scheduleCustomerVisit(@RequestBody CBCustomerVisitsRequest model) {
        boolean success = cbService.scheduleCustomerVisit(model);
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

    @PostMapping("/cb-reschedule-customer-visit")
    public ResponseEntity<?> rescheduleCustomerVisit(@RequestBody CBCustomerVisitsRequest model) {
        boolean success = cbService.rescheduleCustomerVisit(model);




        //Response
        ObjectMapper objectMapper = new ObjectMapper();
        if(success){
            //Object
            ObjectNode node = objectMapper.createObjectNode();
            //successfull message
            node.put("message","Visit Rescheduled Successfully");
//          node.put("id",0);

            return ResponseEntity.ok(new AppResponse(1,node,"Request Processed Successfully"));
        }else{

            //Response
            return ResponseEntity.ok(new AppResponse(0,objectMapper.createObjectNode(),"Request could NOT be processed. Please try again later"));
        }
    }

    @PostMapping(value = "/cb-get-all-customer-visits")
    public ResponseEntity<?> getAllCustomerVisits() {
        List<?>list = cbService.getAllCustomerVisits();
        boolean success = list != null;
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

    @PostMapping("/cb-get-customer-visit-questionnaire")
    public ResponseEntity<?> getCustomerVisitQuestionnaireResponses(@RequestBody CBCustomerVisitQuestionnaireRequest assetManagementRequest) {



        //TODO; INSIDE SERVICE
        boolean success = false;//acquiringService..(model);

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
