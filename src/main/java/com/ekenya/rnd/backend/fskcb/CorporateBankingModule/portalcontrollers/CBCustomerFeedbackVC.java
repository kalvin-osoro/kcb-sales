package com.ekenya.rnd.backend.fskcb.CorporateBankingModule.portalcontrollers;

import com.ekenya.rnd.backend.fskcb.CorporateBankingModule.models.reqs.CBBankingFeedBackRequest;
import com.ekenya.rnd.backend.fskcb.CorporateBankingModule.services.ICBPortalService;
import com.ekenya.rnd.backend.responses.AppResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1")
public class CBCustomerFeedbackVC {
    @Autowired
    private ICBPortalService cbService;


    @PostMapping("/cb-get-customer-feedbacks")
    public ResponseEntity<?> getAllCustomerFeedbacks() {
        List<?>list= cbService.getAllCustomerFeedbacks();
        boolean success = list != null;

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


    @PostMapping("/cb-get-customer-feedback-responses")
    public ResponseEntity<?> getCustomerFeedbackResponses(@RequestBody CBBankingFeedBackRequest model) {
        Object list = cbService.getCustomerFeedbackResponses(model);
        boolean success = list != null;
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
}
