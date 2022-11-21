package com.ekenya.rnd.backend.fskcb.RetailModule.channelcontrollers;

import com.ekenya.rnd.backend.responses.BaseAppResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.JsonObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1")
public class RetailChannelCustomer360VC {

    @PostMapping("/retail-customer-lookup")
    public ResponseEntity<?> lookupCustomer(@RequestParam String account) {


        JsonObject resp = null;//channelService.findCustomerByAccNo(account);//

        //Response
        ObjectMapper objectMapper = new ObjectMapper();
        if(resp == null){
            //Object
            ObjectNode node = objectMapper.createObjectNode();
//          node.put("id",0);

            return ResponseEntity.ok(new BaseAppResponse(1,node,"Request Processed Successfully"));
        }else{

            //Response
            return ResponseEntity.ok(new BaseAppResponse(0,objectMapper.createObjectNode(),"Request could NOT be processed. Please try again later"));
        }
    }


    //CUSTOMER360 VIEW
    @PostMapping("/retail-get-customer-details")
    public ResponseEntity<?> getCustomerDetails(@RequestParam String account) {


        JsonObject resp = null;//channelService.findCustomerByAccNo(account);//

        //Response
        ObjectMapper objectMapper = new ObjectMapper();
        if(resp == null){
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
