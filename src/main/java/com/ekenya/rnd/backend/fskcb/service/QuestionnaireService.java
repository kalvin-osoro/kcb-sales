package com.ekenya.rnd.backend.fskcb.service;

import com.ekenya.rnd.backend.fskcb.payload.QuestionResponseRequest;
import com.ekenya.rnd.backend.fskcb.payload.QuestionTypeRequest;
import com.ekenya.rnd.backend.fskcb.payload.QuestionnaireQuestionRequest;
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
