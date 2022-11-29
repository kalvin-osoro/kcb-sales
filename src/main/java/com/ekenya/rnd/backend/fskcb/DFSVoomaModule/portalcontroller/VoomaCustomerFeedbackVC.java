package com.ekenya.rnd.backend.fskcb.DFSVoomaModule.portalcontroller;

import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.models.reqs.DFSVoomaFeedBackRequest;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.models.reqs.DFSVoomaFeedBackRequestById;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.services.IVoomaPortalService;
import com.ekenya.rnd.backend.responses.BaseAppResponse;
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
public class VoomaCustomerFeedbackVC {
    @Autowired
    IVoomaPortalService portalService;


    @PostMapping("/vooma-get-customer-feedbacks")
    public ResponseEntity<?> getAllCustomerFeedbacks() {
        List<?> list = portalService.getAllCustomerFeedbacks();
        boolean success = list != null;
        ObjectMapper objectMapper = new ObjectMapper();
        if(success){
            //Object
            ArrayNode node = objectMapper.createArrayNode();
            node.addAll((ArrayNode) objectMapper.valueToTree(list));
//          node.put("id",0);

            return ResponseEntity.ok(new BaseAppResponse(1,node,"Request Processed Successfully"));
        }else{

            //Response
            return ResponseEntity.ok(new BaseAppResponse(0,objectMapper.createArrayNode(),"Request could NOT be processed. Please try again later"));
        }
    }


    @PostMapping("/vooma-get-customer-feedback-responses")
    public ResponseEntity<?> getCustomerFeedbackResponses(@RequestBody DFSVoomaFeedBackRequestById model) {
        Object list = portalService.getCustomerFeedbackResponses(model);
        boolean success = list != null;

        //Response
        ObjectMapper objectMapper = new ObjectMapper();
        if(success){
            //Object
            ObjectNode node = objectMapper.createObjectNode();
            node.putArray("responses").addAll((ArrayNode) objectMapper.valueToTree(list));

            return ResponseEntity.ok(new BaseAppResponse(1,node,"Request Processed Successfully"));
        }else{

            //Response
            return ResponseEntity.ok(new BaseAppResponse(0,objectMapper.createArrayNode(),"Request could NOT be processed. Please try again later"));
        }
    }
}
