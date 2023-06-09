package com.ekenya.rnd.backend.fskcb.PremiumSegmentModule.channelcontrollers;

import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.services.IVoomaChannelService;
import com.ekenya.rnd.backend.fskcb.PremiumSegmentModule.models.reps.PSCustomerVisitsByDsrId;
import com.ekenya.rnd.backend.fskcb.PremiumSegmentModule.models.reps.PSCustomerVisitsRequest;
import com.ekenya.rnd.backend.fskcb.PremiumSegmentModule.services.IPSChannelService;
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
public class PSChannelCustomerVisitsVC {

    @Autowired
    IPSChannelService channelService;

    @PostMapping("/ps-create-customer-visit")
    public ResponseEntity<?> createCustomerVisit(@RequestBody PSCustomerVisitsRequest model) {
        boolean success =channelService.createCustomerVisits(model);
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

    @PostMapping("/ps-update-customer-visit")
    public ResponseEntity<?> updateCustomerVisit(@RequestBody PSCustomerVisitsByDsrId model) {
        List<?> dsrVisits = channelService.getAllDsrVisits(model);
        boolean success = dsrVisits !=null;
        //Response
        ObjectMapper objectMapper = new ObjectMapper();
        if(success){
            //Object
            ArrayNode node =objectMapper.createArrayNode();
            node.addAll((List)dsrVisits);

            return ResponseEntity.ok(new BaseAppResponse(1,node,"Request Processed Successfully"));
        }else{

            //Response
            return ResponseEntity.ok(new BaseAppResponse(0,objectMapper.createObjectNode(),"Request could NOT be processed. Please try again later"));
        }
    }

    @PostMapping(value = "/ps-get-all-customer-visits")
    public ResponseEntity<?> getAllCustomerVisitsByDSR(@RequestBody int dsrId) {


        //TODO; INSIDE SERVICE
        boolean success = false;//acquiringService..(model);

        //Response
        ObjectMapper objectMapper = new ObjectMapper();
        if(success){
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
