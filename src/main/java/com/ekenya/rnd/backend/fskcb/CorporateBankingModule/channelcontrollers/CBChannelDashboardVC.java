package com.ekenya.rnd.backend.fskcb.CorporateBankingModule.channelcontrollers;

import com.ekenya.rnd.backend.fskcb.AcquringModule.models.reqs.AcquiringNearbyCustomersRequest;
import com.ekenya.rnd.backend.fskcb.CorporateBankingModule.services.ICBChannelService;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.models.reqs.DSRSummaryRequest;
import com.ekenya.rnd.backend.responses.BaseAppResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/ch")
public class CBChannelDashboardVC {
    @Autowired
    private ICBChannelService channelService;


    @PostMapping("/cb-summary")
    public ResponseEntity<?> getSummary(@RequestBody DSRSummaryRequest model) {

        //Resp =>
        //{
        //    "commission":56000, //calculated commission
        //    "targets-achieved":67, //Summed % per target group
        //    "assigned-leads":4, //Total assigned leads
        //    "customer-visits":9 //Total assigned customer visits
        //}

        ArrayNode dsrSummary= channelService.getDSRSummary(model);
        boolean success = dsrSummary!=null;

        //Response
        ObjectMapper objectMapper = new ObjectMapper();
        if(success){
            //Object
            ObjectNode node = objectMapper.createObjectNode();
            node.put("dsrSummary",dsrSummary);

            return ResponseEntity.ok(new BaseAppResponse(1,node,"Request Processed Successfully"));
        }else{

            //Response
            return ResponseEntity.ok(new BaseAppResponse(0,objectMapper.createObjectNode(),"Request could NOT be processed. Please try again later"));
        }
    }


    @PostMapping("/cb-get-nearby-customers")
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


    @PostMapping("/cb-search-customers")
    public ResponseEntity<?> searchCustomers(@RequestParam String query) {

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


}
