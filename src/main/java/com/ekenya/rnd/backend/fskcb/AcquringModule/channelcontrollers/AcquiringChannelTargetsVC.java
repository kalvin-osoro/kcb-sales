package com.ekenya.rnd.backend.fskcb.AcquringModule.channelcontrollers;


import com.ekenya.rnd.backend.fskcb.AcquringModule.models.AcquiringCustomerVisitsRequest;
import com.ekenya.rnd.backend.fskcb.AcquringModule.services.IAcquiringService;
import com.ekenya.rnd.backend.responses.AppResponse;
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
public class AcquiringChannelTargetsVC {
    @Autowired
    IAcquiringService acquiringService;
    @PostMapping("/acquiring-get-targets-summary")
    public ResponseEntity<?> getTargetsSummary() {


        //TODO; INSIDE SERVICE
        boolean success = false;//acquiringService..(model);

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
}
