package com.deltacode.kcb.service.impl;

import com.deltacode.kcb.entity.QuestionnaireQuestion;
import com.deltacode.kcb.entity.QuestionnaireResponse;
import com.deltacode.kcb.entity.QuestionsType;
import com.deltacode.kcb.entity.UserAccType;
import com.deltacode.kcb.exception.MessageResponse;
import com.deltacode.kcb.payload.*;
import com.deltacode.kcb.repository.QuestionnaireQuestionRepository;
import com.deltacode.kcb.repository.QuestionnaireResponseRepository;
import com.deltacode.kcb.repository.QuestionsTypeRepository;
import com.deltacode.kcb.repository.UserAccTypeRepository;
import com.deltacode.kcb.service.QuestionnaireService;
import com.deltacode.kcb.utils.Status;
import com.deltacode.kcb.utils.Utility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
public class QuestionnaireServiceImpl implements QuestionnaireService {
    @Autowired
    private QuestionsTypeRepository questionsTypeRepository;

    @Autowired
    private QuestionnaireQuestionRepository questionnaireQuestionRepository;

    @Autowired
    private UserAccTypeRepository userAccountTypeRepository;

    @Autowired
    private QuestionnaireResponseRepository questionnaireResponseRepository;



    Logger logger = LoggerFactory.getLogger(QuestionnaireServiceImpl.class);

    @Override
    public ResponseEntity<?> getAllQuestionnaireType() {
        LinkedHashMap<String, Object> responseObject = new LinkedHashMap<>();
        LinkedHashMap<String, Object> responseParams = new LinkedHashMap<>();
        try {
            List<QuestionsType> questionsTypeList =
                    questionsTypeRepository.findAllByStatus(Status.ACTIVE);
            if(questionsTypeList.isEmpty()){
                responseObject.put("status", "success");
                responseObject.put("message", "No question type available");
                responseParams.put("questionsTypeList", questionsTypeList);
                responseObject.put("data", responseParams);
            }else{
                responseObject.put("status", "success");
                responseObject.put("message", "No question type available");
                responseParams.put("questionsTypeList",questionsTypeList);
                responseObject.put("data", responseParams);
            }
            return ResponseEntity.ok().body(responseParams);
        }catch (Exception e){
            return ResponseEntity.ok().body(new MessageResponse(e.getMessage(),"failed"));

        }
    }

    @Override
    public ResponseEntity<?> addQuestionnaireType(QuestionTypeRequest questionTypeRequest) {
        LinkedHashMap<String, Object> responseObject = new LinkedHashMap<>();
        try {
            if(questionTypeRequest==null) throw new RuntimeException("Bad request");
            QuestionsType questionsType = new QuestionsType();
            questionsType.setName(questionTypeRequest.getName());
            questionsType.setStatus(Status.ACTIVE);
            questionsType.setCreatedOn(Utility.getPostgresCurrentTimeStampForInsert());
            questionsType.setCreatedBy(questionTypeRequest.getCreatedBy());
            if(!Utility.validateExpectedResponse(questionTypeRequest.getExpectedResponse()))
                throw new RuntimeException("Expected result is invalid");
            questionsType.setExpectedResponse(questionTypeRequest.getExpectedResponse().trim());
            questionsTypeRepository.save(questionsType);
            responseObject.put("status", "success");
            responseObject.put("message", "Question type created successfully");
            return ResponseEntity.ok().body( responseObject);
        }catch (Exception e){
            return ResponseEntity.ok().body(new MessageResponse(e.getMessage(),"failed"));
        }
    }


    @Override
    public ResponseEntity<?> deleteQuestionnaireType(long id) {
        LinkedHashMap<String, Object> responseObject = new LinkedHashMap<>();
        LinkedHashMap<String, Object> responseParams = new LinkedHashMap<>();
        try{
            QuestionsType questionsType = new QuestionsType();
            Optional<QuestionsType> optionalQuestionsType =
                    questionsTypeRepository.findById(id);
            questionsType = optionalQuestionsType.get();
            questionsType.setStatus(Status.DELETED);
            questionsTypeRepository.save(questionsType);
            responseObject.put("status", "success");
            responseObject.put("message", "Question type "+id
                    +" successfully deleted");
            responseObject.put("data", responseParams);
            return ResponseEntity.ok().body(responseObject);
        }catch (Exception e){
            responseObject.put("status", "failed");
            responseObject.put("message", e.getMessage());
            return ResponseEntity.ok().body(responseObject);
        }
    }

    @Override
    public ResponseEntity<?> addQuestionnaireQuestion(
            QuestionnaireQuestionRequest questionnaireQuestionRequest) {
        LinkedHashMap<String, Object> responseObject = new LinkedHashMap<>();
        try{
            if (questionnaireQuestionRequest == null)throw new RuntimeException("Bad request");
            Optional<UserAccType> optionalUserAccountType = userAccountTypeRepository.findById(
                    questionnaireQuestionRequest.getUserAccountType());
            Optional<QuestionsType> optionalQuestionsType = questionsTypeRepository.findById(
                    questionnaireQuestionRequest.getQuestionType());
            QuestionnaireQuestion questionnaireQuestion = new QuestionnaireQuestion();
            questionnaireQuestion.setUserAccountType(optionalUserAccountType.get());
            questionnaireQuestion.setQuestion(questionnaireQuestionRequest.getQuestion());
            questionnaireQuestion.setQuestionDescription(
                    questionnaireQuestionRequest.getQuestionDescription());
            questionnaireQuestion.setQuestionType(optionalQuestionsType.get());
            questionnaireQuestion.setStatus(Status.ACTIVE);
            questionnaireQuestion.setChoices(questionnaireQuestionRequest.getChoices());

            questionnaireQuestion.setCreatedOn(Utility.getPostgresCurrentTimeStampForInsert());
            questionnaireQuestionRepository.save(questionnaireQuestion);
            responseObject.put("status", "success");
            responseObject.put("message", "Question successfully created");
            return ResponseEntity.ok().body(responseObject);
        }catch (Exception e){
            responseObject.put("status", "failed");
            responseObject.put("message", e.getMessage());
            return ResponseEntity.ok().body(responseObject);
        }
    }

    @Override
    public ResponseEntity<?> getQuestionnaireQuestionsByUserAccountType(long id) {
        LinkedHashMap<String, Object> responseObject = new LinkedHashMap<>();
        LinkedHashMap<String, Object> responseParams = new LinkedHashMap<>();
        try{
            Optional<UserAccType> optionalUserAccountType =
                    userAccountTypeRepository.findById(id);
            UserAccType userAccountType = optionalUserAccountType.get();
            List<QuestionnaireQuestion> questionnaireQuestionList =
                    questionnaireQuestionRepository.findAllByUserAccountType(userAccountType);
            List<QuestionsResponse> questionsResponseList = questionnaireQuestionList.stream().
                    map(res -> new QuestionsResponse(res.getId(), res.getQuestion(),
                            res.getQuestionDescription(), res.getQuestionType(),
                            res.getStatus(), res.getChoices())).collect(Collectors.toList());
            if(questionsResponseList.isEmpty()){
                responseObject.put("status", "success");
                responseObject.put("message", "No questions available for " +
                        ""+userAccountType.getUserAccTypeName());
                responseParams.put("questionnaireQuestions",questionsResponseList);
                responseObject.put("data", responseParams);
            }else {
                responseObject.put("status", "success");
                responseObject.put("message", "Questions available");
                responseParams.put("questionnaireQuestions",questionsResponseList);
                responseObject.put("data", responseParams);
            }
            return ResponseEntity.ok().body(responseObject);
        }catch (Exception e){
            return ResponseEntity.ok().body(new MessageResponse(e.getMessage(),"failed"));
        }
    }

    @Override
    public ResponseEntity<?> deleteQuestionnaireQuestions(long id) {
        LinkedHashMap<String, Object> responseObject = new LinkedHashMap<>();
        LinkedHashMap<String, Object> responseParams = new LinkedHashMap<>();
        try{
            Optional<QuestionnaireQuestion> optionalQuestionnaireQuestion =
                    questionnaireQuestionRepository.findById(id);
            QuestionnaireQuestion questionnaireQuestion = optionalQuestionnaireQuestion.get();
            questionnaireQuestion.setStatus(Status.DELETED);
            responseObject.put("status", "success");
            responseObject.put("message", "Question successfully deleted");
            responseObject.put("data", responseParams);
            return ResponseEntity.ok().body(responseObject);
        }catch (Exception e){
            return ResponseEntity.ok().body(new MessageResponse(e.getMessage(),"failed"));
        }

    }

    @Override
    public ResponseEntity<?> addQuestionnaireResponse(
            QuestionResponseRequest questionResponseRequest, HttpServletRequest httpServletRequest) {
        try {
            if (questionResponseRequest == null) throw new Exception("Bad request");

            UserDetails userDetailsObject = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getDetails();
            if (userDetailsObject == null)throw new RuntimeException("Service error");
            String sysUserId = userDetailsObject.getUsername();
            List<QuestionnaireResponseRequest> questionnaireResponseRequestList =
                    questionResponseRequest.getListQuestionResponse();
            questionnaireResponseRequestList.forEach(q ->{
                Optional<QuestionnaireQuestion> optionalQuestionnaireQuestion;
                QuestionnaireResponse questionnaireResponse;
                questionnaireResponse = new QuestionnaireResponse();
                questionnaireResponse.setAccountNumber(questionResponseRequest.getAccountId());
                questionnaireResponse.setStatus(Status.ACTIVE);
                try {
                    questionnaireResponse.setCreatedOn(Utility.getPostgresCurrentTimeStampForInsert());
                } catch (Exception e) {
                    logger.info("Error is ", e.getMessage());
                }
                questionnaireResponse.setCreatedBy(sysUserId);
                optionalQuestionnaireQuestion = questionnaireQuestionRepository.findById(
                        q.getQuestionnaireQuestion());
                questionnaireResponse.setQuestionnaireQuestion(optionalQuestionnaireQuestion.get());
                questionnaireResponse.setQuestionResponse(
                        q.getQuestionResponse());
                questionnaireResponseRepository.save(questionnaireResponse);
            });
            return ResponseEntity.ok().body(new MessageResponse("Response saved", "success"));
        }catch (Exception e){
            return ResponseEntity.ok().body(new MessageResponse(e.getMessage(), "failed"));
        }
    }

    @Override
    public ResponseEntity<?> getQuestionnaireResponse() {
        LinkedHashMap<String, Object> responseObject = new LinkedHashMap<>();
        LinkedHashMap<String, Object> responseParams = new LinkedHashMap<>();
        try {
            List<QuestionnaireResponse> questionnaireResponseList =
                    questionnaireResponseRepository.findAll();
            if (questionnaireResponseList.isEmpty()){
                responseObject.put("status", "success");
                responseObject.put("message", "No response available");
                responseParams.put("questionresponse",questionnaireResponseList);
                responseObject.put("data", responseParams);
            }else {
                responseObject.put("status", "success");
                responseObject.put("message", "Bank branches available");
                responseParams.put("questionresponse",questionnaireResponseList);
                responseObject.put("data", responseParams);
            }
            return ResponseEntity.ok().body(responseObject);
        }catch (Exception e){
            return ResponseEntity.ok().body(new MessageResponse(e.getMessage(),"failed"));
        }
    }

    @Override
    public ResponseEntity<?> deleteQuestionnaireResponse() {
        LinkedHashMap<String, Object> responseObject = new LinkedHashMap<>();
        LinkedHashMap<String, Object> responseParams = new LinkedHashMap<>();
        try {
            List<QuestionnaireResponse> questionnaireResponseList =
                    questionnaireResponseRepository.findAll();
            if (questionnaireResponseList.isEmpty()){
                responseObject.put("status", "success");
                responseObject.put("message", "No response available");
                responseParams.put("questionresponse",questionnaireResponseList);
                responseObject.put("data", responseParams);
            }else {
                questionnaireResponseList.forEach(q ->{
                    q.setStatus(Status.DELETED);
                    questionnaireResponseRepository.save(q);
                });
                responseObject.put("status", "success");
                responseObject.put("message", "Response successfully deleted");
                responseParams.put("questionresponse",questionnaireResponseList);
                responseObject.put("data", responseParams);
            }
            return ResponseEntity.ok().body(responseObject);
        }catch (Exception e){
            return ResponseEntity.ok().body(new MessageResponse(e.getMessage(),"failed"));
        }
    }
}
