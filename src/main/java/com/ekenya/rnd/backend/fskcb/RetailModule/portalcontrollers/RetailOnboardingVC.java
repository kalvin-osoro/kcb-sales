package com.ekenya.rnd.backend.fskcb.RetailModule.portalcontrollers;

import com.ekenya.rnd.backend.fskcb.PersonalBankingModule.models.PBApproveMerchantOnboarindRequest;
import com.ekenya.rnd.backend.fskcb.RetailModule.models.RetailApproveMerchantOnboarindRequest;
import com.ekenya.rnd.backend.fskcb.RetailModule.services.IRetailService;
import com.ekenya.rnd.backend.fskcb.service.CustomerDetailsService;
import com.ekenya.rnd.backend.responses.AppResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@RestController("/api/retail")
public class RetailOnboardingVC {
    @Autowired
    IRetailService retailService;



    //List all onboarded merchants
    @RequestMapping(value = "/retail-get-all-onboarded-customers", method = RequestMethod.GET)
    public ResponseEntity<?> getAllOnboardings() {

        //TODO; INSIDE SERVICE
        boolean success = false;//acquiringService..(model);

        //Response
        ObjectMapper objectMapper = new ObjectMapper();
        if(success){
            //Object
            ArrayNode node = objectMapper.createArrayNode();
//          node.put("id",0);

            return ResponseEntity.ok(new AppResponse(1,node,"Request Processed Successfully"));
        }else{

            //Response
            return ResponseEntity.ok(new AppResponse(0,objectMapper.createArrayNode(),"Request could NOT be processed. Please try again later"));
        }
    }


    @PostMapping("/retail-approve-onboarding")
    public ResponseEntity<?> approveOnboarding(@RequestBody RetailApproveMerchantOnboarindRequest assetManagementRequest) {


        //TODO; INSIDE SERVICE
        boolean success = false;//acquiringService..(model);

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
