package com.ekenya.rnd.backend.fskcb.AgencyBankingModule.portalcontrollers;

import com.ekenya.rnd.backend.fskcb.AcquringModule.models.reqs.AcquiringApproveMerchant;
import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.datasource.entities.AgencyOnboardingEntity;
import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.services.IAgencyPortalService;
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
public class AgencyOnboardingVC {

    @Autowired
    IAgencyPortalService acquiringService;

    //List all onboarded merchants
    @PostMapping(value = "/agency-get-all-onboarded-customers")
    public ResponseEntity<?> getAllOnboardings() {
        List<?> list = acquiringService.loadAllOnboardedAgents();
        boolean success = list != null;


        //Response
        ObjectMapper objectMapper = new ObjectMapper();
        if(success){
            //Object
            ArrayNode node = objectMapper.createArrayNode();
            node.addAll((List)list);
//          node.put("id",0);

            return ResponseEntity.ok(new BaseAppResponse(1,node,"Request Processed Successfully"));
        }else{

            //Response
            return ResponseEntity.ok(new BaseAppResponse(0,objectMapper.createArrayNode(),"Request could NOT be processed. Please try again later"));
        }
    }


    @PostMapping("/agency-approve-onboarding")
    public ResponseEntity<?> approveMerchantOnboarding(@RequestBody AcquiringApproveMerchant model) {

        boolean success = acquiringService.approveAgentOnboarding(model);
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
    @PostMapping("/agency-reject-onboarding")
    public ResponseEntity<?> rejectMerchantOnboarding(@RequestBody AcquiringApproveMerchant model) {

        boolean success = acquiringService.rejectAgentOnboarding(model);
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
