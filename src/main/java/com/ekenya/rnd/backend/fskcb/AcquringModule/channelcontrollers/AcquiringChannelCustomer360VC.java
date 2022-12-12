package com.ekenya.rnd.backend.fskcb.AcquringModule.channelcontrollers;

import com.ekenya.rnd.backend.fskcb.AcquringModule.models.reqs.CustomerDetailsRequest;
import com.ekenya.rnd.backend.fskcb.AcquringModule.services.IAcquiringChannelService;
import com.ekenya.rnd.backend.fskcb.CrmAdapters.services.ICRMService;
import com.ekenya.rnd.backend.responses.BaseAppResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(path = "/api/v1/ch")
public class AcquiringChannelCustomer360VC {

    @Autowired
    IAcquiringChannelService channelService;
    @Autowired
    ICRMService crmService;

    @PostMapping("/acquiring-customer-lookup")
    public ResponseEntity<?> lookupCustomer(@RequestParam String account) {


        JsonObject resp = channelService.findCustomerByAccNo(account);//

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
    @PostMapping("/acquiring-get-customer-details")
    public ResponseEntity<?> getCustomerDetails(@RequestBody CustomerDetailsRequest model) {


        JsonObject resp = crmService.getCustomerDetails(model.getAccountNo());

        //Response
        ObjectMapper objectMapper = new ObjectMapper();
        if(resp != null){
            //Object
            ObjectNode node = objectMapper.createObjectNode();
            node.put("customer",resp.toString());

            return ResponseEntity.ok(new BaseAppResponse(1,node,"Request Processed Successfully"));
        }else{

                //Response
                return ResponseEntity.ok(new BaseAppResponse(0,objectMapper.createObjectNode(),"Request could NOT be processed. Please try again later"));
        }

    }
}
