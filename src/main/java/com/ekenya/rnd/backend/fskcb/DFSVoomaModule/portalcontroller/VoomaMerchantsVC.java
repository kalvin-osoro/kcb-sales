package com.ekenya.rnd.backend.fskcb.DFSVoomaModule.portalcontroller;

import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.models.reqs.VoomaMerchantDetailsRequest;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.services.IVoomaPortalService;
import com.ekenya.rnd.backend.responses.BaseAppResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1")
public class VoomaMerchantsVC {

    @Autowired
    IVoomaPortalService acquiringService;

    @PostMapping("/vooma-get-merchant-by-id")
    public ResponseEntity<?> getMerchantById(@RequestBody VoomaMerchantDetailsRequest model) {
        Object merchant = acquiringService.getMerchantById(model);
        boolean success = merchant != null;



        //Response
        ObjectMapper objectMapper = new ObjectMapper();
        if(success){
            //return merchant object
            ObjectNode node = objectMapper.createObjectNode();
            node.putArray("merchant").add(objectMapper.valueToTree(merchant));

            return ResponseEntity.ok(new BaseAppResponse(1,node,"Request Processed Successfully"));
        }else{

            //Response
            return ResponseEntity.ok(new BaseAppResponse(0,objectMapper.createObjectNode(),"Request could NOT be processed. Please try again later"));
        }
    }

//    @PostMapping(value = "/vooma-get-all-merchants")
//    public ResponseEntity<?> getAllMerchantOnboarded() {
//
//
//        //TODO;
//        boolean success = false;//acquiringService..(model);
//
//        //Response
//        ObjectMapper objectMapper = new ObjectMapper();
//        if(success){
//            //Object
//            ArrayNode node = objectMapper.createArrayNode();
////          node.put("id",0);
//
//            return ResponseEntity.ok(new AppResponse(1,node,"Request Processed Successfully"));
//        }else{
//
//            //Response
//            return ResponseEntity.ok(new AppResponse(0,objectMapper.createArrayNode(),"Request could NOT be processed. Please try again later"));
//        }
//    }
}
