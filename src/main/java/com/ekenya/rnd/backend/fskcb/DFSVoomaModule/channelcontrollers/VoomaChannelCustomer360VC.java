package com.ekenya.rnd.backend.fskcb.DFSVoomaModule.channelcontrollers;

import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.models.reqs.CustomerRequest;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.services.IVoomaChannelService;
import com.ekenya.rnd.backend.responses.BaseAppResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/ch/")
public class VoomaChannelCustomer360VC {

    @Autowired
    IVoomaChannelService channelService;

    @PostMapping("/vooma-customer-lookup")
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
    @PostMapping("/vooma-get-customer-details")
    public ResponseEntity<?> getCustomerDetails(@RequestBody CustomerRequest model) {


        ObjectNode resp = channelService.getCustomerDetails(model);
        boolean success = resp != null;
        //Response
        ObjectMapper objectMapper = new ObjectMapper();
        if(success){
            ObjectNode node = objectMapper.createObjectNode();
            node.setAll(resp);

            return ResponseEntity.ok(new BaseAppResponse(1,node,"Request Processed Successfully"));
        }else{

                //Response
                return ResponseEntity.ok(new BaseAppResponse(0,objectMapper.createObjectNode(),"Request could NOT be processed. Please try again later"));
        }
    }

}
