package com.ekenya.rnd.backend.fskcb.PremiumSegmentModule.portalcontrollers;

import com.ekenya.rnd.backend.fskcb.AcquringModule.models.AcquiringAddQuestionnaireRequest;
import com.ekenya.rnd.backend.fskcb.PremiumSegmentModule.services.IPSPortalService;
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
public class PSSetupsVC {

    @Autowired
    IPSPortalService portalService;

    @PostMapping("/ps-create-questionnaire")
    public ResponseEntity<?> createQuestionnaire(@RequestBody AcquiringAddQuestionnaireRequest model) {

        boolean success = portalService.createQuestionnaire(model);

        //Response
        ObjectMapper objectMapper = new ObjectMapper();
        if(success){
            //Object
            ObjectNode node = objectMapper.createObjectNode();
            node.put("message", "Questionnaire created successfully");
//          node.put("id",0);

            return ResponseEntity.ok(new BaseAppResponse(1,node,"Request Processed Successfully"));
        }else{

            //Response
            return ResponseEntity.ok(new BaseAppResponse(0,objectMapper.createObjectNode(),"Request could NOT be processed. Please try again later"));
        }
    }

    @PostMapping(value = "/ps-get-all-questionnaires")
    public ResponseEntity<?> getAllQuestionnaires() {

        //
        List<?> list = portalService.loadQuestionnaires();
        boolean success = list != null;

        //Response
        ObjectMapper objectMapper = new ObjectMapper();
        if(list != null ){
            //Object
            ArrayNode node = objectMapper.valueToTree(list);
            node.addAll((ArrayNode) objectMapper.valueToTree(list));
//          node.put("id",0);

            return ResponseEntity.ok(new BaseAppResponse(1,node,"Request Processed Successfully"));
        }else{

            //Response
            return ResponseEntity.ok(new BaseAppResponse(0,objectMapper.createObjectNode(),"Request could NOT be processed. Please try again later"));
        }
    }
}
