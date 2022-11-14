package com.ekenya.rnd.backend.fskcb.DFSVoomaModule.portalcontroller;

import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.models.VoomaAddQuestionnaireRequest;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.services.IVoomaService;
import com.ekenya.rnd.backend.fskcb.payload.ProductRequest;
import com.ekenya.rnd.backend.responses.AppResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1")
public class VoomaSetupsVC {
    
    @Autowired
    IVoomaService voomaService;

    @PostMapping("/vooma-create-questionnaire")
    public ResponseEntity<?> createQuestionnaire(@RequestBody VoomaAddQuestionnaireRequest assetManagementRequest) {


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

    @RequestMapping(value = "/vooma-get-all-questionnaires", method = RequestMethod.GET)
    public ResponseEntity<?> getAllQuestionnaires() {

        //
        List<ObjectNode> list = null;//acquiringService.loadQuestionnaires();

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
