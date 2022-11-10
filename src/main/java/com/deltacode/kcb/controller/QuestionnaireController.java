package com.deltacode.kcb.controller;


import com.deltacode.kcb.payload.QuestionResponseRequest;
import com.deltacode.kcb.payload.QuestionTypeRequest;
import com.deltacode.kcb.payload.QuestionnaireQuestionRequest;
import com.deltacode.kcb.service.QuestionnaireService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/questionnaire")
public class QuestionnaireController {

    private final  QuestionnaireService questionnaireService;

    public QuestionnaireController(QuestionnaireService questionnaireService) {
        this.questionnaireService = questionnaireService;
    }

    @PostMapping("/get-question-type")
    public ResponseEntity<?> getQuestionType() {
        return questionnaireService.getAllQuestionnaireType();
    }
    @PostMapping("/add-question-type")
    public ResponseEntity<?> addQuestionType(@RequestBody QuestionTypeRequest questionTypeRequest){
        return questionnaireService.addQuestionnaireType(questionTypeRequest);
    }

    @PostMapping("/delete-question-type/{id}")
    public ResponseEntity<?> deleteQuestionType(@PathVariable long id) {
        return questionnaireService.deleteQuestionnaireType(id);
    }

    @PostMapping("/add-question")
    public ResponseEntity<?> addQuestion(@RequestBody
                                                 QuestionnaireQuestionRequest questionnaireQuestionRequest){
        return questionnaireService.addQuestionnaireQuestion(questionnaireQuestionRequest);
    }

    @PostMapping("/get-question-by-user-type/{id}")
    public ResponseEntity<?> getQuestionByUserType(@PathVariable long id) {
        return questionnaireService.getQuestionnaireQuestionsByUserAccountType(id);
    }
    @PostMapping("/delete-question/{id}")
    public ResponseEntity<?> deleteQuestion(@PathVariable long id) {
        return questionnaireService.deleteQuestionnaireQuestions(id);
    }

    @PostMapping("/questionnaire-response")
    public ResponseEntity<?> questionResponse(
            @RequestBody QuestionResponseRequest questionResponseRequest, HttpServletRequest httpServletRequest) {
        return questionnaireService.addQuestionnaireResponse(questionResponseRequest, httpServletRequest);
    }

    @GetMapping("/get-questionnaire-response")
    public ResponseEntity<?> getQuestionResponse(){
        return questionnaireService.getQuestionnaireResponse();
    }
}
