package com.ekenya.rnd.backend.fskcb.CorporateBankingModule.channelcontrollers;

import com.ekenya.rnd.backend.fskcb.CorporateBankingModule.models.reqs.CBAddCustomerAppointmentRequest;
import com.ekenya.rnd.backend.fskcb.CorporateBankingModule.models.reqs.CBCustomerVisitReportRequest;
import com.ekenya.rnd.backend.fskcb.CorporateBankingModule.models.reqs.CBCustomerVisitsRequest;
import com.ekenya.rnd.backend.fskcb.CorporateBankingModule.models.reqs.CBDSROpportunity;
import com.ekenya.rnd.backend.fskcb.CorporateBankingModule.services.ICBChannelService;
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
public class CBChannelCustomerVisitsVC {

    @Autowired
    ICBChannelService channelService;

    @PostMapping("/cb-create-customer-visit")
    public ResponseEntity<?> createCustomerVisit(@RequestBody CBCustomerVisitsRequest request) {
        boolean success = channelService.createCustomerVisit(request);
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

    @PostMapping("/cb-update-customer-visit")
    public ResponseEntity<?> updateCustomerVisit(@RequestBody CBCustomerVisitsRequest request) {


        //TODO; INSIDE SERVICE
        boolean success = false;//acquiringService..(model);

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

    @PostMapping(value = "/cb-get-all-customer-visits")
    public ResponseEntity<?> getAllCustomerVisitsByDSR(@RequestBody CBCustomerVisitsRequest model) {
        List<?> visits = channelService.getAllCustomerVisitsByDSR(model);
        boolean success = visits != null;

        //Response
        ObjectMapper objectMapper = new ObjectMapper();
        if(success){
            //Object
            ArrayNode node = objectMapper.createArrayNode();
            node.addAll((List)visits);
//          node.put("id",0);

            return ResponseEntity.ok(new BaseAppResponse(1,node,"Request Processed Successfully"));
        }else{

            //Response
            return ResponseEntity.ok(new BaseAppResponse(0,objectMapper.createArrayNode(),"Request could NOT be processed. Please try again later"));
        }
    }
        @PostMapping(value = "/cb-get-all-rm-opportubity")
        public ResponseEntity<?> getAllCustomerOpportunityByDSR(@RequestBody CBDSROpportunity model) {
            List<?> visits = channelService.getAllCustomeropportunityByDSR(model);
            boolean success = visits != null;

            //Response
            ObjectMapper objectMapper = new ObjectMapper();
            if(success){
                //Object
                ArrayNode node = objectMapper.createArrayNode();
                node.addAll((List)visits);
//          node.put("id",0);

                return ResponseEntity.ok(new BaseAppResponse(1,node,"Request Processed Successfully"));
            }else{

                //Response
                return ResponseEntity.ok(new BaseAppResponse(0,objectMapper.createArrayNode(),"Request could NOT be processed. Please try again later"));
            }
    }



}
