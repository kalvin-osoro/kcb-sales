package com.ekenya.rnd.backend.fskcb.AcquringModule.portalcontrollers;

import com.ekenya.rnd.backend.fskcb.AcquringModule.models.AcquringSummaryRequest;
import com.ekenya.rnd.backend.fskcb.AcquringModule.services.IAcquiringPortalService;
import com.ekenya.rnd.backend.responses.AppResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1")
public class AcquiringReportsVC {

    @Autowired
    IAcquiringPortalService acquiringService;



    //get onboarding summary for last 7 days based on filters
    @PostMapping(value = "/acquiring-get-onboarding-summary", method = RequestMethod.POST)
    public ResponseEntity<?> getOnboardingSummary(@RequestBody AcquringSummaryRequest filters) {
        //Expected Response structure
        //Take last 7 days
        //[{
        //    "MID":"",
        //    "merchant_name":"",
        //    "date_onboarded":"dd-MMM-yyyy",
        //    "onboarding_status":"pending",//approved, rejected
        //    "coordinates":{"lat":"","lng":""]
        //}]

        //Response
        List<?> list = acquiringService.getOnboardingSummary(filters);
        boolean success = list != null;


        //Response
        ObjectMapper objectMapper = new ObjectMapper();
        if(success){
            ArrayNode node = objectMapper.createArrayNode();
            node.addAll((ArrayNode) objectMapper.valueToTree(list));
            //Object
            return ResponseEntity.ok(new AppResponse(1,node,"Request Processed Successfully"));
        }else{
            //Response
            return ResponseEntity.ok(new AppResponse(0,objectMapper.createObjectNode(),"Request could NOT be processed. Please try again later"));
        }
    }

    @PostMapping(value = "/acquiring-targets-summary", method = RequestMethod.POST)
    public ResponseEntity<?> getTargetsSummary(@RequestBody AcquringSummaryRequest filters) {
        List<?> list = acquiringService.getTargetsSummary(filters);


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
        boolean success = list == null;

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

    @PostMapping(value = "/acquiring-leads-summary", method = RequestMethod.POST)
    public ResponseEntity<?> getLeadsSummary(@RequestBody AcquringSummaryRequest filters) {
        List<?> list = acquiringService.getLeadsSummary(filters);
        boolean success = list == null;



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


    @PostMapping(value = "/acquiring-assets-summary")
    public ResponseEntity<?> getAssetsSummary(@RequestBody AcquringSummaryRequest filters) {
        List<?> list = acquiringService.getAssetsSummary(filters);
        boolean success = list == null;


        //Expected Response structure
        //Take last 7 days
        //[{
        //    "faulty_assets":"400",
        //    "working_assets":"500",
        //    "assigned_assets":"500",
        //    "unassigned_assets":"500",
        //    "assets_loc":[{"serial":"","latlng":{"lat":"","lng":""},"pic":"/gg.png"},{}],
        //}]


        //TODO;

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
