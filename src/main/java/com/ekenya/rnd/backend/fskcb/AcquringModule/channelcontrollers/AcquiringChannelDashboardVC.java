package com.ekenya.rnd.backend.fskcb.AcquringModule.channelcontrollers;

import com.ekenya.rnd.backend.fskcb.AcquringModule.models.AcquiringCustomerVisitsRequest;
import com.ekenya.rnd.backend.fskcb.AcquringModule.models.reqs.AcquiringNearbyCustomersRequest;
import com.ekenya.rnd.backend.fskcb.AcquringModule.services.IAcquiringChannelService;
import com.ekenya.rnd.backend.responses.BaseAppResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/ch")
public class AcquiringChannelDashboardVC {
    @Autowired
   private IAcquiringChannelService acquiringService;

    @PostMapping("/acquiring-summary")
    public ResponseEntity<?> getSummary() {

        //Resp =>
        //{
        //    "commission":56000, //calculated commission
        //    "targets-achieved":67, //Summed % per target group
        //    "assigned-leads":4, //Total assigned leads
        //    "customer-visits":9 //Total assigned customer visits
        //}

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


    @PostMapping("/acquiring-get-nearby-customers")
    public ResponseEntity<?> getNearbyCustomers(@RequestBody AcquiringNearbyCustomersRequest request) {

        //Resp =>
        //{
        //    "customers":[{
        //        "name":"ABC Merchants",
        //        "mid":100,
        //        "addr":"",
        //        "phoneNo":"",
        //        "type":"",
        //        "latlng":{
        //            "lat":8.4395345,
        //            "lng":43.56876
        //        }
        //    }],
        //    "region":{
        //        "name":"Nairobi West",
        //        "bounds":[{
        //            "lat":8.4395345,
        //            "lng":43.56876
        //        },{
        //            "lat":8.4395345,
        //            "lng":43.56876
        //        }]
        //    }
        //}

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


    @PostMapping("/acquiring-search-customers")
    public ResponseEntity<?> searchCustomers(@RequestParam String merchantName, @RequestParam String merchantPhone) {
        Object obj = acquiringService.searchCustomers(merchantName, merchantPhone);
        boolean success = obj != null;

        //Resp =>
        //{
        //    "total-results":23,
        //    "customers":[{
        //        "name":"ABC Merchants",
        //        "mid":100,
        //        "addr":"",
        //        "phoneNo":"",
        //        "type":"",
        //        "latlng":{
        //            "lat":8.4395345,
        //            "lng":43.56876
        //        }
        //    }]
        //}

        //Response
        ObjectMapper objectMapper = new ObjectMapper();
        if(success){
            //Object
            ObjectNode node = objectMapper.createObjectNode();
            node.putArray("customers").addAll((ArrayNode) obj);


            return ResponseEntity.ok(new BaseAppResponse(1,node,"Request Processed Successfully"));
        }else{

            //Response
            return ResponseEntity.ok(new BaseAppResponse(0,objectMapper.createObjectNode(),"Request could NOT be processed. Please try again later"));
        }
    }


}
