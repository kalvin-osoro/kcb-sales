package com.ekenya.rnd.backend.fskcb.AcquringModule.portalcontrollers;

import com.ekenya.rnd.backend.fskcb.AcquringModule.models.AcquiringMerchantDetailsRequest;
import com.ekenya.rnd.backend.fskcb.AcquringModule.models.reqs.AssignMerchant;
import com.ekenya.rnd.backend.fskcb.AcquringModule.models.reqs.DSRMerchantRequest;
import com.ekenya.rnd.backend.fskcb.AcquringModule.services.IAcquiringPortalService;
import com.ekenya.rnd.backend.fskcb.CorporateBankingModule.models.reqs.CBAssignLeadRequest;
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
public class AcquiringMerchantsVC {

    @Autowired
    IAcquiringPortalService acquiringService;

    @PostMapping("/acquiring-get-merchant-by-id")
    public ResponseEntity<?> getMerchantById(@RequestBody AcquiringMerchantDetailsRequest acquiringMerchantDetailsRequest) {
        Object merchant = acquiringService.getMerchantById(acquiringMerchantDetailsRequest);
        boolean success = merchant  != null;
        //Response
        ObjectMapper objectMapper = new ObjectMapper();
        if(success){
            //Object
            ObjectNode node = objectMapper.createObjectNode();
            node.putPOJO("merchant",merchant);
//          node.put("id",0);

            return ResponseEntity.ok(new BaseAppResponse(1,node,"Request Processed Successfully"));
        }else{

            //Response
            return ResponseEntity.ok(new BaseAppResponse(0,objectMapper.createObjectNode(),"Request could NOT be processed. Please try again later"));
        }

    }

    @PostMapping("/acquiring-assign-merchant")
    public ResponseEntity<?> assignMerchant(@RequestBody AssignMerchant model) {
        boolean success = acquiringService.assignMerchantToDSR(model);
        //Response
        ObjectMapper objectMapper = new ObjectMapper();
        if(success){
            //Object
            ArrayNode node = objectMapper.createArrayNode();
//          node.put("id",0);

            return ResponseEntity.ok(new BaseAppResponse(1,node,"Request Processed Successfully"));
        }else{

            //Response
            return ResponseEntity.ok(new BaseAppResponse(0,objectMapper.createArrayNode(),"Request could NOT be processed. Please try again later"));
        }
    }

    @PostMapping(value = "/acquiring-get-all-approved-merchants")
    public ResponseEntity<?> getAllApprovedMerchant() {
        List<?> list = acquiringService.loadAllApprovedMerchants();
        //
        boolean success  = list != null;

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
    //get merhant by dsrId
    @PostMapping(value = "/acquiring-get-all-dsr-merchant")
    public ResponseEntity<?> getDsrMerchant(@RequestBody DSRMerchantRequest model) {
        List<?> list = acquiringService.getDsrMerchant(model);
        //
        boolean success  = list != null;

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

}
