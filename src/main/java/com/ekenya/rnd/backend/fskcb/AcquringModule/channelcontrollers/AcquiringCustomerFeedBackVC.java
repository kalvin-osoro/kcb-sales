package com.ekenya.rnd.backend.fskcb.AcquringModule.channelcontrollers;

import com.ekenya.rnd.backend.fskcb.AcquringModule.models.reqs.CustomerFeedbackRequest;
import com.ekenya.rnd.backend.fskcb.AcquringModule.services.IAcquiringChannelService;
import com.ekenya.rnd.backend.responses.BaseAppResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/ch")
public class AcquiringCustomerFeedBackVC {

@Autowired
    IAcquiringChannelService acquiringChannelService;
@Autowired
    ObjectMapper objectMapper;
    @PostMapping("/acquiring-create-customer-feedback")
    ResponseEntity<?> createCustomerFeedback(@RequestBody CustomerFeedbackRequest model) {
        boolean success = acquiringChannelService.createCustomerFeedback(model);
        //Response
        ObjectMapper objectMapper = new ObjectMapper();
        if(success){
            ObjectNode node = objectMapper.createObjectNode();

                return ResponseEntity.ok(new BaseAppResponse(1,node,"Request Processed Successfully"));
            }else{

                //Response
                return ResponseEntity.ok(new BaseAppResponse(0,objectMapper.createArrayNode(),"Request could NOT be processed. Please try again later"));
            }
        }
}
