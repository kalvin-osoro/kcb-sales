package com.ekenya.rnd.backend.fskcb.PersonalBankingModule.portalcontrollers;

import com.ekenya.rnd.backend.fskcb.AcquringModule.models.AcquiringApproveMerchantOnboarindRequest;
import com.ekenya.rnd.backend.fskcb.PersonalBankingModule.models.PBApproveMerchantOnboarindRequest;
import com.ekenya.rnd.backend.fskcb.PersonalBankingModule.services.IPBService;
import com.ekenya.rnd.backend.fskcb.service.FileStorageService;
import com.ekenya.rnd.backend.responses.AppResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(path = "/api/v1")
public class PBOnboardingVC {
    @Autowired
    private FileStorageService fileStorageService;
    @Autowired
    IPBService pbService;



    //List all onboarded merchants
    @RequestMapping(value = "/pb-get-all-onboarded-customers", method = RequestMethod.GET)
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


    @PostMapping("/pb-approve-onboarding")
    public ResponseEntity<?> approveOnboarding(@RequestBody PBApproveMerchantOnboarindRequest assetManagementRequest) {


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
