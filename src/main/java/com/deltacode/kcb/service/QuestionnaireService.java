package com.deltacode.kcb.service;

import com.deltacode.kcb.payload.QuestionResponseRequest;
import com.deltacode.kcb.payload.QuestionTypeRequest;
import com.deltacode.kcb.payload.QuestionnaireQuestionRequest;
import com.deltacode.kcb.payload.QuestionnaireResponseRequest;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;

public interface QuestionnaireService {
    ResponseEntity<?> getAllQuestionnaireType();
    ResponseEntity<?> addQuestionnaireType(QuestionTypeRequest questionTypeRequest);
    ResponseEntity<?> deleteQuestionnaireType(long id);

    ResponseEntity<?> addQuestionnaireQuestion(
            QuestionnaireQuestionRequest questionnaireQuestionRequest);
    ResponseEntity<?> getQuestionnaireQuestionsByUserAccountType( long id );
    ResponseEntity<?> deleteQuestionnaireQuestions(long id);


    ResponseEntity<?> addQuestionnaireResponse(QuestionResponseRequest questionResponseRequest,
                                               HttpServletRequest httpServletRequest);
    ResponseEntity<?> getQuestionnaireResponse();
    ResponseEntity<?> deleteQuestionnaireResponse();

}
