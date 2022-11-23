package com.ekenya.rnd.backend.fskcb.AcquringModule.channelcontrollers;

import com.ekenya.rnd.backend.fskcb.AcquringModule.models.AcquiringCustomerVisitsRequest;
import com.ekenya.rnd.backend.fskcb.AcquringModule.services.IAcquiringChannelService;
import com.ekenya.rnd.backend.fskcb.AcquringModule.services.IAcquiringPortalService;
import com.ekenya.rnd.backend.responses.BaseAppResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/ch")
public class AcquiringChannelCustomerVisitsVC {

    @Autowired
    IAcquiringChannelService acquiringService;
    @PostMapping("/acquiring-create-customer-visit")
    public ResponseEntity<?> createAcquiringCustomerVisit(@RequestBody AcquiringCustomerVisitsRequest model) {
        boolean success = acquiringService.createCustomerVisit(model);
        //Response
        ObjectMapper objectMapper = new ObjectMapper();
        if(success){
            //Object
            ObjectNode node = objectMapper.createObjectNode();

            return ResponseEntity.ok(new BaseAppResponse(1,node,"Request Processed Successfully"));
        }else{

            //Response
            return ResponseEntity.ok(new BaseAppResponse(0,objectMapper.createObjectNode(),"Request could NOT be processed. Please try again later"));
        }
    }

    @PostMapping("/acquiring-update-customer-visit")
    public ResponseEntity<?> updateAcquiringCustomerVisit(@RequestBody AcquiringCustomerVisitsRequest model) {
        boolean success = acquiringService.updateCustomerVisit(model);
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

    @PostMapping(value = "/acquiring-get-all-customer-visits")
    public ResponseEntity<?> getAllAcquiringCustomerVisitsByDSR(@RequestBody int dsrId) {
        List<?> customerVisits = acquiringService.getAllCustomerVisitsByDSR(dsrId);
        boolean success = customerVisits  != null;
        //Response
        ObjectMapper objectMapper = new ObjectMapper();
        if(success){
            //Object
            ArrayNode node = objectMapper.createArrayNode();
            node.addAll((List)customerVisits);
//          node.put("id",0);

            return ResponseEntity.ok(new BaseAppResponse(1,node,"Request Processed Successfully"));
        }else{

            //Response
            return ResponseEntity.ok(new BaseAppResponse(0,objectMapper.createArrayNode(),"Request could NOT be processed. Please try again later"));
        }
    }

}
