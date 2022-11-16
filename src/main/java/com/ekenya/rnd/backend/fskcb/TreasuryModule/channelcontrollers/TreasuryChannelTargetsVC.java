package com.ekenya.rnd.backend.fskcb.TreasuryModule.channelcontrollers;


import com.ekenya.rnd.backend.fskcb.PremiumSegmentModule.services.IPSChannelService;
import com.ekenya.rnd.backend.fskcb.TreasuryModule.services.ITreasuryChannelService;
import com.ekenya.rnd.backend.responses.AppResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/ch")
public class TreasuryChannelTargetsVC {


    @Autowired
    ITreasuryChannelService channelService;


    @PostMapping("/treasury-get-targets-summary")
    public ResponseEntity<?> getTargetsSummary() {



        //INSIDE SERVICE
        ObjectNode resp = channelService.targetsSummary();

        //Response
        ObjectMapper objectMapper = new ObjectMapper();
        if(resp != null){
            //Object

            return ResponseEntity.ok(new AppResponse(1,resp,"Request Processed Successfully"));
        }else{

            //Response
            return ResponseEntity.ok(new AppResponse(0,objectMapper.createObjectNode(),"Request could NOT be processed. Please try again later"));
        }
    }
}
