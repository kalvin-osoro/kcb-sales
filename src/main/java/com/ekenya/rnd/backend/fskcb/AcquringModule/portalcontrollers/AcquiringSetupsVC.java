package com.ekenya.rnd.backend.fskcb.AcquringModule.portalcontrollers;

import com.ekenya.rnd.backend.fskcb.AcquringModule.models.AcquiringAddQuestionnaireRequest;
import com.ekenya.rnd.backend.fskcb.AcquringModule.services.IAcquiringService;
import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.models.AgencyAssetRequest;
import com.ekenya.rnd.backend.responses.AppResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1")
public class AcquiringSetupsVC {

    @Autowired
    IAcquiringService acquiringService;

    @PostMapping("/acquiring-create-questionnaire")
    public ResponseEntity<?> createQuestionnaire(@RequestBody AcquiringAddQuestionnaireRequest assetManagementRequest) {


        //TODO;
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

    @RequestMapping(value = "/acquiring-get-all-questionnaires", method = RequestMethod.GET)
    public ResponseEntity<?> getAllQuestionnaires() {

        //
        List<ObjectNode> list = acquiringService.loadQuestionnaires();

        //Response
        ObjectMapper objectMapper = new ObjectMapper();
        if(list != null ){
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
