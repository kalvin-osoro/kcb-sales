package com.ekenya.rnd.backend.fskcb.TreasuryModule.channelcontrollers;

import com.ekenya.rnd.backend.fskcb.TreasuryModule.models.reqs.TreasuryCustomerVisitQuestionnaireRequest;
import com.ekenya.rnd.backend.fskcb.TreasuryModule.models.reqs.TreasuryCustomerVisitsRequest;
import com.ekenya.rnd.backend.fskcb.TreasuryModule.services.ITreasuryChannelService;
import com.ekenya.rnd.backend.responses.BaseAppResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1")
public class TreasuryChannelCustomer360VC {
    @Autowired
    ITreasuryChannelService channelService;

    @PostMapping("/treasury-schedule-customer-visit")
    public ResponseEntity<?> scheduleCustomerVisit(@RequestBody TreasuryCustomerVisitsRequest request) {


        //TODO; INSEIDE SERVICE
        boolean success = channelService.attemptScheduleCustomerVisit(request);

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

    @PostMapping("/treasury-reschedule-customer-visit")
    public ResponseEntity<?> rescheduleCustomerVisit(@RequestBody TreasuryCustomerVisitsRequest request) {


        //INSIDE SERVICE
        boolean success = channelService.attemptRescheduleCustomerVisit(request);

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

    @PostMapping(value = "/treasury-get-all-customer-visits")
    public ResponseEntity<?> getAllCustomerVisits() {


        //
        ArrayNode list = channelService.loadCustomerVisits();

        //Response
        ObjectMapper objectMapper = new ObjectMapper();
        if(list != null){
            //Object
            //ArrayNode node = objectMapper.createArrayNode();
//          node.put("id",0);

            return ResponseEntity.ok(new BaseAppResponse(1,list,"Request Processed Successfully"));
        }else{

            //Response
            return ResponseEntity.ok(new BaseAppResponse(0,objectMapper.createArrayNode(),"Request could NOT be processed. Please try again later"));
        }
    }



    @PostMapping("/treasury-get-customer-visit-questionnaire")
    public ResponseEntity<?> getCustomerVisitQuestionnaireResponses(@RequestBody TreasuryCustomerVisitQuestionnaireRequest request) {

        //
        ArrayNode list = channelService.loadCustomerVisitQuestionnaireResponses(request);

        //Response
        ObjectMapper objectMapper = new ObjectMapper();
        if(list != null){
            //Object
            ArrayNode node = objectMapper.createArrayNode();
//          node.put("id",0);

            return ResponseEntity.ok(new BaseAppResponse(1,node,"Request Processed Successfully"));
        }else{

            //Response
            return ResponseEntity.ok(new BaseAppResponse(0,objectMapper.createArrayNode(),"Request could NOT be processed. Please try again later"));
        }
    }

}
