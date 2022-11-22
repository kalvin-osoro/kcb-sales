package com.ekenya.rnd.backend.fskcb.PersonalBankingModule.portalcontrollers;

import com.ekenya.rnd.backend.fskcb.PersonalBankingModule.models.reqs.PBSummaryRequest;
import com.ekenya.rnd.backend.fskcb.PersonalBankingModule.services.IPBPortalService;
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
public class PBDashboardsVC {
    //

    @Autowired
    IPBPortalService pbService;

    @PostMapping("/pb-onboarding-summary")
    public ResponseEntity<?> getOnboardingSummary(@RequestBody PBSummaryRequest filters) {
        List<?>list =pbService.getOnboardingSummary(filters);
        boolean success =list !=null;







        //Response
        ObjectMapper objectMapper = new ObjectMapper();
        if(success){
            //Object
            ArrayNode node = objectMapper.createArrayNode();
            node.addAll((ArrayNode) objectMapper.valueToTree(list));

            return ResponseEntity.ok(new BaseAppResponse(1,node,"Request Processed Successfully"));
        }else{

            //Response
            return ResponseEntity.ok(new BaseAppResponse(0,objectMapper.createObjectNode(),"Request could NOT be processed. Please try again later"));
        }
    }


    @PostMapping(value = "/pb-customer-visits-summary")
    public ResponseEntity<?> getCustomerVisitsSummary(@RequestBody PBSummaryRequest filters) {



           List<?>list =pbService.getCustomerVisitsSummary(filters);
        boolean success =list !=null;


        //TODO;

        //Response
        ObjectMapper objectMapper = new ObjectMapper();
        if(success){
            //Object
            ArrayNode node = objectMapper.createArrayNode();
            node.addAll((ArrayNode) objectMapper.valueToTree(list));

            return ResponseEntity.ok(new BaseAppResponse(1,node,"Request Processed Successfully"));
        }else{

            //Response
            return ResponseEntity.ok(new BaseAppResponse(0,objectMapper.createObjectNode(),"Request could NOT be processed. Please try again later"));
        }
    }


    @PostMapping(value = "/pb-targets-summary")
    public ResponseEntity<?> getTargetsSummary(@RequestBody PBSummaryRequest filters) {
        List<?>list =pbService.getTargetsSummary(filters);
        boolean success =list !=null;


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

        //Response
        ObjectMapper objectMapper = new ObjectMapper();
        if(success){
            //Object
            ArrayNode node = objectMapper.createArrayNode();
            node.addAll((ArrayNode) objectMapper.valueToTree(list));

            return ResponseEntity.ok(new BaseAppResponse(1,node,"Request Processed Successfully"));
        }else{

            //Response
            return ResponseEntity.ok(new BaseAppResponse(0,objectMapper.createObjectNode(),"Request could NOT be processed. Please try again later"));
        }
    }

    @PostMapping(value = "/pb-leads-summary")
    public ResponseEntity<?> getLeadsSummary(@RequestBody PBSummaryRequest filters) {
        List<?>list =pbService.getLeadsSummary(filters);
        boolean success =list !=null;


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
//          node.put("id",0);
            ArrayNode node = objectMapper.createArrayNode();
            node.addAll((ArrayNode) objectMapper.valueToTree(list));

            return ResponseEntity.ok(new BaseAppResponse(1,node,"Request Processed Successfully"));
        }else{

            //Response
            return ResponseEntity.ok(new BaseAppResponse(0,objectMapper.createObjectNode(),"Request could NOT be processed. Please try again later"));
        }
    }


    @PostMapping(value = "/pb-assets-summary")
    public ResponseEntity<?> getAssetsSummary(@RequestBody PBSummaryRequest filters) {


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
