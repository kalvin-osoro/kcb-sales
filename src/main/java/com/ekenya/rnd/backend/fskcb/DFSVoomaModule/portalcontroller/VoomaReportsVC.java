package com.ekenya.rnd.backend.fskcb.DFSVoomaModule.portalcontroller;

import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.models.reqs.VoomaSummaryRequest;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.services.IVoomaPortalService;
import com.ekenya.rnd.backend.responses.BaseAppResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1")
public class VoomaReportsVC {
    //

    @Autowired
    IVoomaPortalService voomaService;

    @PostMapping("/vooma-onboarding-summary")
    public ResponseEntity<?> getOnboardingSummary() {

        List<?> list = voomaService.getOnboardingSummary();
        boolean success = list != null;
        //Expected Response structure
        //Take last 7 days
        //[{
        //    "mID":"",
        //    "merchant_name":"",
        //    "date_onboarded":"dd-MMM-yyyy",
        //    "onboarding_status":"pending",//approved, rejected
        //    "coordinates":{"lat":"","lng":""]},
        //}]




        //TODO;

        //Response
        ObjectMapper objectMapper = new ObjectMapper();
        if(success){
            //Object
            ArrayNode node = objectMapper.createArrayNode();
            node.addAll((ArrayNode) objectMapper.valueToTree(list));
//          node.put("id",0);

            return ResponseEntity.ok(new BaseAppResponse(1,node,"Request Processed Successfully"));
        }else{

            //Response
            return ResponseEntity.ok(new BaseAppResponse(0,objectMapper.createObjectNode(),"Request could NOT be processed. Please try again later"));
        }
    }


    @PostMapping(value = "/vooma-customer-visits-summary")
    public ResponseEntity<?> getCustomerVisitsSummary(@RequestBody VoomaSummaryRequest filters) {



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


    @PostMapping(value = "/vooma-targets-summary")
    public ResponseEntity<?> getTargetsSummary(@RequestBody VoomaSummaryRequest filters) {


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

    @PostMapping(value = "/vooma-leads-summary")
    public ResponseEntity<?> getLeadsSummary(@RequestBody VoomaSummaryRequest filters) {


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


    @PostMapping(value = "/vooma-assets-summary")
    public ResponseEntity<?> getAssetsSummary(@RequestBody VoomaSummaryRequest filters) {


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

    @PostMapping("/vooma-onboarding-summaryv1")
    public ResponseEntity<?> getOnboardingSummaryv1() {

        List<?> list = voomaService.getOnboardingSummaryv1();
        boolean success = list != null;


        //Response
        ObjectMapper objectMapper = new ObjectMapper();
        if(success){
            //Object
            ArrayNode node = objectMapper.createArrayNode();
            node.addAll((ArrayNode) objectMapper.valueToTree(list));
//          node.put("id",0);

            return ResponseEntity.ok(new BaseAppResponse(1,node,"Request Processed Successfully"));
        }else{

            //Response
            return ResponseEntity.ok(new BaseAppResponse(0,objectMapper.createObjectNode(),"Request could NOT be processed. Please try again later"));
        }
    }
}
