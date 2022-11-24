package com.ekenya.rnd.backend.fskcb.DFSVoomaModule.channelcontrollers;

import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.models.reqs.VoomaCustomerVisitsRequest;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.services.IVoomaChannelService;
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
public class VoomaChannelCustomerVisitsVC {

    @Autowired
    IVoomaChannelService channelService;

    @PostMapping("/vooma-create-customer-visit")
    public ResponseEntity<?> createCustomerVisit(@RequestBody VoomaCustomerVisitsRequest model) {
        boolean success = channelService.createCustomerVisit(model);
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

    @PostMapping("/vooma-update-customer-visit")
    public ResponseEntity<?> updateCustomerVisit(@RequestBody VoomaCustomerVisitsRequest request) {

        boolean success = channelService.updateCustomerVisit(request);
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

    @PostMapping(value = "/vooma-get-all-customer-visits")
    public ResponseEntity<?> getAllCustomerVisitsByDSR(@RequestBody VoomaCustomerVisitsRequest model) {
        List<?> customerVisits = channelService.getAllCustomerVisitsByDSR(model);
        boolean success = customerVisits != null;
        //Response
        ObjectMapper objectMapper = new ObjectMapper();
        if(success){
            //Object
            ArrayNode node = objectMapper.createArrayNode();
            node.addAll((List) customerVisits);
//          node.put("id",0);

            return ResponseEntity.ok(new BaseAppResponse(1,node,"Request Processed Successfully"));
        }else{

            //Response
            return ResponseEntity.ok(new BaseAppResponse(0,objectMapper.createArrayNode(),"Request could NOT be processed. Please try again later"));
        }
    }

}
