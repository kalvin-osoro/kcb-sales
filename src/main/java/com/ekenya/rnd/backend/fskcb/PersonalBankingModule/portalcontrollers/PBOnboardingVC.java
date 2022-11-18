package com.ekenya.rnd.backend.fskcb.PersonalBankingModule.portalcontrollers;

import com.ekenya.rnd.backend.fskcb.PersonalBankingModule.datasource.entities.PSBankingOnboardingEntity;
import com.ekenya.rnd.backend.fskcb.PersonalBankingModule.services.IPBPortalService;
import com.ekenya.rnd.backend.fskcb.PremiumSegmentModule.models.reps.PSApproveMerchantOnboarindRequest;
import com.ekenya.rnd.backend.fskcb.files.IFileStorageService;
import com.ekenya.rnd.backend.responses.AppResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1")
public class PBOnboardingVC {
    @Autowired
    private IFileStorageService IFileStorageService;
    @Autowired
    IPBPortalService pbService;



    //List all onboarded merchants
    @PostMapping(value = "/pb-get-all-onboarded-customers")
    public ResponseEntity<?> getAllOnboardings() {
        List<?> list =pbService.getAllOnboardings();
        boolean success = list!=null;


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
            return ResponseEntity.ok(new AppResponse(0,objectMapper.createArrayNode(),"Request could NOT be processed. Please try again later"));
        }
    }


    @PostMapping("/pb-approve-onboarding")
    public ResponseEntity<?> approveOnboarding(@RequestBody PSBankingOnboardingEntity model) {
        Object onboarding = pbService.approveOnboarding(model);
        boolean success = onboarding!=null;
        //Response
        ObjectMapper objectMapper = new ObjectMapper();
        if(success){
            //Object
            Object node = objectMapper.createObjectNode();
            node = objectMapper.valueToTree(onboarding);

//          node.put("id",0);

            return ResponseEntity.ok(new AppResponse(1,node,"Request Processed Successfully"));
        }else{

            //Response
            return ResponseEntity.ok(new AppResponse(0,objectMapper.createObjectNode(),"Request could NOT be processed. Please try again later"));
        }
    }
}
