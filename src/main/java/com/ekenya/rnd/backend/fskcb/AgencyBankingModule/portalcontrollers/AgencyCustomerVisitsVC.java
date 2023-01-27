package com.ekenya.rnd.backend.fskcb.AgencyBankingModule.portalcontrollers;

import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.models.reqs.*;
import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.services.IAgencyPortalService;
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
public class AgencyCustomerVisitsVC {
    @Autowired
    IAgencyPortalService agencyService;
    
    @PostMapping("/agency-schedule-customer-visit")
    public ResponseEntity<?> scheduleCustomerVisit(@RequestBody AgencyCustomerVisitsRequest agencyCustomerVisitsRequest) {
        //TODO; INSIDE SERVICE
        boolean success = agencyService.scheduleCustomerVisit(agencyCustomerVisitsRequest);


        //Response
        ObjectMapper objectMapper = new ObjectMapper();
        if(success){
            //Object
            ObjectNode node = objectMapper.createObjectNode();
//          node.put("id",0);

            return ResponseEntity.ok(new BaseAppResponse(1,node,"Request Processed Successfully"));
        }else{

            //Response
            return ResponseEntity.ok(new BaseAppResponse(0,objectMapper.createObjectNode(),"Request could NOT be processed. Please try again later"));
        }
    }

    @PostMapping("/agency-reschedule-customer-visit")
    public ResponseEntity<?> rescheduleCustomerVisit(@RequestBody AgencyRescheduleVisitsRequest assetManagementRequest) {
        //TODO; INSIDE SERVICE
        boolean success = agencyService.reScheduleCustomerVisit(assetManagementRequest);

        //Response
        ObjectMapper objectMapper = new ObjectMapper();
        if(success){
            //Object
            ObjectNode node = objectMapper.createObjectNode();
//          node.put("id",0);

            return ResponseEntity.ok(new BaseAppResponse(1,node,"Request Processed Successfully"));
        }else{

            //Response
            return ResponseEntity.ok(new BaseAppResponse(0,objectMapper.createObjectNode(),"Request could NOT be processed. Please try again later"));
        }
    }




    @PostMapping("/agency-get-customer-visit-questionnaire-response")
        public ResponseEntity<?> getCustomerVisitQuestionnaireResponse(@RequestBody AgencyCustomerVisitQuestionnaireRequest agencyCustomerVisitQuestionnaireRequest) {
            //TODO; INSIDE SERVICE
        List<?> agencyBankingCustomerVisit = agencyService.getCustomerVisitQuestionnaireResponse(agencyCustomerVisitQuestionnaireRequest);
            boolean success = agencyBankingCustomerVisit != null;
        //Response
        ObjectMapper objectMapper = new ObjectMapper();
        if(success){
            //Object
            ArrayNode node = objectMapper.createArrayNode();
            node.addAll((ArrayNode) objectMapper.valueToTree(agencyBankingCustomerVisit));
//          node.put("id",0);

            return ResponseEntity.ok(new BaseAppResponse(1,node,"Request Processed Successfully"));
        }else{

            //Response
            return ResponseEntity.ok(new BaseAppResponse(0,objectMapper.createArrayNode(),"Request could NOT be processed. Please try again later"));
        }
    }

    @PostMapping(value = "/agency-get-all-visits")
    public ResponseEntity<?> getAllOnboardingV2() {
        List<?> list = agencyService.getAllVisitsV2();

        //Response
        ObjectMapper objectMapper = new ObjectMapper();
        if(list !=null){
            return ResponseEntity.ok(new BaseAppResponse(1,list,"Request Processed Successfully"));

        }else{

            //Response
            return ResponseEntity.ok(new BaseAppResponse(0,objectMapper.createArrayNode(),"Request could NOT be processed. Please try again later"));
        }
    }
    @PostMapping("/agency-get-customer-visit-by-id")
    public ResponseEntity<?> getAgencyVisitById(@RequestBody AgencyVisitRequest model  ) {
        Object asset = agencyService.getCustomerVisitById(model);
        boolean success = asset  != null;
        //Response
        ObjectMapper objectMapper = new ObjectMapper();
        if(success){
            //Object
            ObjectNode node = objectMapper.createObjectNode();
            node.putPOJO("customerVisit",asset);
//          node.put("id",0);

            return ResponseEntity.ok(new BaseAppResponse(1,node,"Request Processed Successfully"));
        }else{

            //Response
            return ResponseEntity.ok(new BaseAppResponse(0,objectMapper.createObjectNode(),"Request could NOT be processed. Please try again later"));
        }
    }

    //create visits question for acquiring and agency
    @PostMapping("/create-acquiring-agency-visit-question")
    public ResponseEntity<?> craeteVisitQuestion(@RequestBody AgencyCollectAssetRequest.AgencyBankingQuestionnareQuestionRequest model) {
        boolean success = agencyService.createVisitQuestion(model);
        //Response
        ObjectMapper objectMapper = new ObjectMapper();
        if(success){
            //Object
            ObjectNode node = objectMapper.createObjectNode();
//          node.put("id",0);

            return ResponseEntity.ok(new BaseAppResponse(1,node,"Request Processed Successfully"));
        }else{

            //Response
            return ResponseEntity.ok(new BaseAppResponse(0,objectMapper.createObjectNode(),"Request could NOT be processed. Please try again later"));
        }
    }

}
