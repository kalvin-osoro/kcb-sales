package com.ekenya.rnd.backend.fskcb.CorporateBankingModule.portalcontrollers;

import com.ekenya.rnd.backend.fskcb.CorporateBankingModule.models.reqs.QuestionResponseRequest;
import com.ekenya.rnd.backend.fskcb.CorporateBankingModule.models.reqs.QuestionTypeRequest;
import com.ekenya.rnd.backend.fskcb.CorporateBankingModule.models.reqs.UQuestionnaireQuestionRequest;
import com.ekenya.rnd.backend.fskcb.CorporateBankingModule.services.ICBPortalService;
import com.ekenya.rnd.backend.responses.BaseAppResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class QuestionnareTypeVC {
    @Autowired
    ICBPortalService cbPortalService;

    @PostMapping("/create-questionnare-type")
    ResponseEntity<?> createQuestionnareType(QuestionTypeRequest model) {
        boolean success = cbPortalService.createQuestionnareType(model);
        ObjectMapper objectMapper = new ObjectMapper();
        if (success) {
            ObjectNode node = objectMapper.createObjectNode();
            node.put("message", "Questionnare type created successfully");
            return ResponseEntity.ok(new BaseAppResponse(1, node, "Request Processed Successfully"));
        } else {
            return ResponseEntity.ok(new BaseAppResponse(0, objectMapper.createObjectNode(), "Request could NOT be processed. Please try again later"));

        }
    }
    //get all questions types
    @PostMapping("/get-all-questionnare-types")
    ResponseEntity<?> getAllQuestionnareTypes() {
        List<?> response = cbPortalService.getAllQuestionnareTypes();
        boolean success = response != null;
        ObjectMapper objectMapper = new ObjectMapper();
        if (success) {
            ArrayNode node = objectMapper.createArrayNode();
            return ResponseEntity.ok(new BaseAppResponse(1, node, "Request Processed Successfully"));
        } else {
            return ResponseEntity.ok(new BaseAppResponse(0, objectMapper.createObjectNode(), "Request could NOT be processed. Please try again later"));

        }
    }
    @PostMapping("/add-questionnare-questions")
    ResponseEntity<?> addQuestionnareQuestions(UQuestionnaireQuestionRequest model) {
boolean success = cbPortalService.addQuestionnareQuestions(model);
        ObjectMapper objectMapper = new ObjectMapper();
        if (success) {
            ObjectNode node = objectMapper.createObjectNode();
            node.put("message", "Questionnare questions added successfully");
            return ResponseEntity.ok(new BaseAppResponse(1, node, "Request Processed Successfully"));
        } else {
            return ResponseEntity.ok(new BaseAppResponse(0, objectMapper.createObjectNode(), "Request could NOT be processed. Please try again later"));

        }

    }
    //add questionnare Response
    @PostMapping("/add-questionnare-response")
    ResponseEntity<?> addQuestionnareResponse(QuestionResponseRequest model) {
        boolean success = cbPortalService.addQuestionnareResponse(model);
        ObjectMapper objectMapper = new ObjectMapper();
        if (success) {
            ObjectNode node = objectMapper.createObjectNode();
            node.put("message", "Questionnare response added successfully");
            return ResponseEntity.ok(new BaseAppResponse(1, node, "Request Processed Successfully"));
        } else {
            return ResponseEntity.ok(new BaseAppResponse(0, objectMapper.createObjectNode(), "Request could NOT be processed. Please try again later"));

        }

    }
}


