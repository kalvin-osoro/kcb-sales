package com.ekenya.rnd.backend.fskcb.DFSVoomaModule.portalcontroller;

import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.models.QuestionnaireWrapper;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.models.reqs.AssignFeedBackRequest;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.models.reqs.GetRQuestionnaireRequest;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.models.reqs.QuestionnaireRequest;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.services.IVoomaPortalService;
import com.ekenya.rnd.backend.responses.BaseAppResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping(path = "/api/v1")
public class QuestionnaireVC1 {
    @Autowired
  private IVoomaPortalService voomaPortalService;

//    UniversalResponse{
//        private String message;
//        private Integer status;
//        private Object data;
//    }


    @PostMapping("/vooma-create-questionnaire1")
    public ResponseEntity<?> createQuestionnaire1(@RequestBody QuestionnaireRequest model) {

        boolean success= voomaPortalService.addQuestionnaire(model);

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

    @PostMapping("/vooma-get-questionnaire1")
    public ResponseEntity<?> getAllQuestionnairev1() {
        List<?> questionnaireV1 = voomaPortalService.getAllAllQuestionnaireV1();
        boolean success = questionnaireV1 != null;

        //Response
        ObjectMapper objectMapper = new ObjectMapper();
        if(success){
            //Object
            ArrayNode node = objectMapper.createArrayNode();
            node.addAll((List)questionnaireV1);

            return ResponseEntity.ok(new BaseAppResponse(1,node,"Request Processed Successfully"));
        }else{

            //Response
            return ResponseEntity.ok(new BaseAppResponse(0,objectMapper.createArrayNode(),"Request could NOT be processed. Please try again later"));
        }
    }
    @PostMapping("/get-questionnaire-response")
    public ResponseEntity<?> getQuestionnaireById(@RequestBody GetRQuestionnaireRequest model) {
        ObjectNode objectNode = voomaPortalService.getQuestionnaireResponses(model);
        boolean success = objectNode != null;

        //Response
        ObjectMapper objectMapper = new ObjectMapper();
        if(success){
            //Object
            ObjectNode node = objectMapper.createObjectNode();
            node.putArray("responses").add(objectMapper.valueToTree(objectNode));

            return ResponseEntity.ok(new BaseAppResponse(1,node,"Request Processed Successfully"));
        }else{

            //Response
            return ResponseEntity.ok(new BaseAppResponse(0,objectMapper.createArrayNode(),"Request could NOT be processed. Please try again later"));
        }
    }

    //disable questionnaire
    @PostMapping("/disable-questionnaire")
    public ResponseEntity<?> disableQuestionnaire(@RequestBody GetRQuestionnaireRequest model) {
        boolean success = voomaPortalService.disableQuestionnaire(model);
        //Response
        ObjectMapper objectMapper = new ObjectMapper();
        if(success){
            //Object
            ObjectNode node = objectMapper.createObjectNode();
            return ResponseEntity.ok(new BaseAppResponse(1,node,"Request Processed Successfully"));
        }else{

            //Response
            return ResponseEntity.ok(new BaseAppResponse(0,objectMapper.createObjectNode(),"Request could NOT be processed. Please try again later"));
        }
    }
    @PostMapping("/assign-feedback")
    public ResponseEntity<?> assignFeedBack(@RequestBody AssignFeedBackRequest model) {
        boolean success = voomaPortalService.assignFeedback(model);
        //Response
        ObjectMapper objectMapper = new ObjectMapper();
        if(success){
            //Object
            ObjectNode node = objectMapper.createObjectNode();
            return ResponseEntity.ok(new BaseAppResponse(1,node,"Request Processed Successfully"));
        }else{

            //Response
            return ResponseEntity.ok(new BaseAppResponse(0,objectMapper.createObjectNode(),"Request could NOT be processed. Please try again later"));
        }
    }
    //get questionnaire by profileCode and QuestionnaireType
    @PostMapping("/get-questionnaire-by-profileCode")
    public ResponseEntity<?> getQuestionnaireByProfileCode(@RequestBody QuestionnaireWrapper model) {
        List<ObjectNode> list = voomaPortalService.getQuestionnaireByProfileCode(model);
        boolean success = list != null;

        //Response
        ObjectMapper objectMapper = new ObjectMapper();
        if(success){
            //Object
            ArrayNode node = objectMapper.createArrayNode();
            node.addAll((List)list);

            return ResponseEntity.ok(new BaseAppResponse(1,node,"Request Processed Successfully"));
        }else{

            //Response
            return ResponseEntity.ok(new BaseAppResponse(0,objectMapper.createArrayNode(),"Request could NOT be processed. Please try again later"));
        }
    }
}
