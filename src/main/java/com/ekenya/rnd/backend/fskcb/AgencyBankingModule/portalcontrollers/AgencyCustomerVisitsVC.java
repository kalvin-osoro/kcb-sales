package com.ekenya.rnd.backend.fskcb.AgencyBankingModule.portalcontrollers;

import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.models.reqs.AgencyCustomerVisitQuestionnaireRequest;
import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.models.reqs.AgencyCustomerVisitsRequest;
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
    public ResponseEntity<?> rescheduleCustomerVisit(@RequestBody AgencyCustomerVisitsRequest assetManagementRequest) {
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

   //get all customer visits using @postmapping
    @PostMapping("/agency-get-all-customer-visits")
    public ResponseEntity<?> getAllCustomerVisits() {
        //TODO; INSIDE SERVICE
        List<?> customerVisits = agencyService.getAllCustomerVisits();
        boolean success = customerVisits != null;

        //Response
        ObjectMapper objectMapper = new ObjectMapper();
        if(success){
            //Object
            ArrayNode node = objectMapper.createArrayNode();
            node.addAll((ArrayNode) objectMapper.valueToTree(customerVisits));
//          node.put("id",0);

            return ResponseEntity.ok(new BaseAppResponse(1,node,"Request Processed Successfully"));
        }else{

            //Response
            return ResponseEntity.ok(new BaseAppResponse(0,objectMapper.createArrayNode(),"Request could NOT be processed. Please try again later"));
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

}
