package com.ekenya.rnd.backend.fskcb.RetailModule.portalcontrollers;

import com.ekenya.rnd.backend.fskcb.RetailModule.models.reqs.RetailSummaryRequest;
import com.ekenya.rnd.backend.fskcb.RetailModule.services.IRetailPortalService;
import com.ekenya.rnd.backend.responses.BaseAppResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1")
public class RetailDashboardsVC {
    //

    @Autowired
    IRetailPortalService retailService;

    @PostMapping("/retail-onboarding-summary")
    public ResponseEntity<?> getOnboardingSummary(@RequestBody RetailSummaryRequest filters) {

        //Expected Response structure
        //Take last 7 days
        //[{
        //    "mID":"",
        //    "merchant_name":"",
        //    "date_onboarded":"dd-MMM-yyyy",
        //    "onboarding_status":"pending",//approved, rejected
        //    "coordinates":{"lat":"","lng":""]
        //}]




        //TODO;
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


    @PostMapping(value = "/retail-customer-visits-summary")
    public ResponseEntity<?> getCustomerVisitsSummary(@RequestBody RetailSummaryRequest filters) {



        //Expected Response structure
        //Take last 7 days
        //[{
        //    "mID":"",
        //    "merchant_name":"",
        //    "date_visited":"dd-MMM-yyyy",
        //    "visits_status":"upcoming",//completed, fail/expired
        //    "coordinates":{"lat":"","lng":""]
        //}]


        //TODO;
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


    @PostMapping(value = "/retail-targets-summary")
    public ResponseEntity<?> getTargetsSummary(@RequestBody RetailSummaryRequest filters) {


        //Expected Response structure
        //Take last 7 days
        //[{
        //    "target_id":"",
        //    "target_name":"",
        //    "target_value":"",
        //    "target_achieved":"", //Sum of value achieved by department staff
        //    "start_date":"dd-MMM-yyyy",
        //    "target_status":"active",//completed, expired
        //}]


        //TODO;
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

    @PostMapping(value = "/retail-leads-summary")
    public ResponseEntity<?> getLeadsSummary(@RequestBody RetailSummaryRequest filters) {


        //Expected Response structure
        //Take last 7 days
        //[{
        //    "leads_originated":"400",
        //    "leads_assigned":"500",
        //    "leads_open":"500",
        //    "leads_closed":"500",
        //    "open_leads_by_status":{
        //          "hot":500,
        //            "warm":300,
        //                "cold":78
        //    },
        //    "leads":[{"topic":"","latlng":{"lat":"","lng":""},"date_originated":"dd-mmm-yyyy"},{}],
        //    "our_leads_summary":["date":"dd-mmm-yyyy",{"hot":67,"warm":400,"cold":3}},{}],
        //}]


        //TODO;
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


    @PostMapping(value = "/retail-assets-summary")
    public ResponseEntity<?> getAssetsSummary(@RequestBody RetailSummaryRequest filters) {


        //Expected Response structure
        //Take last 7 days
        //[{
        //    "faulty_assets":"400",
        //    "working_assets":"500",
        //    "assigned_assets":"500",
        //    "unassigned_assets":"500",
        //    "open_leads_by_status":{
        //          "hot":500,
        //            "warm":300,
        //                "cold":78
        //    },
        //    "assets_loc":[{"serial":"","latlng":{"lat":"","lng":""},"pic":"/gg.png"},{}],
        //}]


        //TODO;
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
