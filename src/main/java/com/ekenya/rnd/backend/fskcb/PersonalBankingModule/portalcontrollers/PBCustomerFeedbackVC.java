package com.ekenya.rnd.backend.fskcb.PersonalBankingModule.portalcontrollers;

import com.ekenya.rnd.backend.fskcb.PersonalBankingModule.models.reqs.PSBankingFeedBackRequest;
import com.ekenya.rnd.backend.fskcb.PersonalBankingModule.services.IPBPortalService;
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
public class PBCustomerFeedbackVC {
    @Autowired
    private  IPBPortalService pbService;


    @PostMapping("/pb-get-customer-feedbacks")
    public ResponseEntity<?> getAllCustomerFeedbacks() {
        List<?>list =pbService.getAllCustomerFeedbacks();
        boolean success =list !=null;
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


    @PostMapping("/pb-get-customer-feedback-responses")
    public ResponseEntity<?> getCustomerFeedbackResponses(@RequestBody PSBankingFeedBackRequest psBankingFeedBackRequest) {
        Object list = pbService.getCustomerFeedbackResponses(psBankingFeedBackRequest);
        boolean success = list != null;
        //Response
        ObjectMapper objectMapper = new ObjectMapper();
        if(success){
            //Object
            ObjectNode node = objectMapper.createObjectNode();
            node.putArray("responses").addAll((ArrayNode) objectMapper.valueToTree(list));

            return ResponseEntity.ok(new AppResponse(1,node,"Request Processed Successfully"));
        }else{

            //Response
            return ResponseEntity.ok(new AppResponse(0,objectMapper.createArrayNode(),"Request could NOT be processed. Please try again later"));
        }
    }
}
