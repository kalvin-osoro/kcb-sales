package com.ekenya.rnd.backend.fskcb.CorporateBankingModule.portalcontrollers;

import com.ekenya.rnd.backend.fskcb.CorporateBankingModule.services.ICBPortalService;
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
public class CBDashboardsController {
    @Autowired
    private  ICBPortalService cbService;
    //

    @PostMapping("/cb-onboarding-summary")
    public ResponseEntity<?> getOnboardingSummary() {
        List<?>list=cbService.getOnboardingSummary();
        boolean success = list != null;

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

        //Response
        ObjectMapper objectMapper = new ObjectMapper();
        if(success){
            //Object
            ArrayNode node = objectMapper.createArrayNode();
            node.addAll((ArrayNode) objectMapper.valueToTree(list));
//          node.put("id",0);

            return ResponseEntity.ok(new AppResponse(1,node,"Request Processed Successfully"));
        }else{

            //Response
            return ResponseEntity.ok(new AppResponse(0,objectMapper.createObjectNode(),"Request could NOT be processed. Please try again later"));
        }
    }
}
