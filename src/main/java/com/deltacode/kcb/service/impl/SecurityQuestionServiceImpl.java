package com.deltacode.kcb.service.impl;

import com.deltacode.kcb.entity.SecurityQuestion;
import com.deltacode.kcb.payload.SecurityQuestionRequest;
import com.deltacode.kcb.repository.SecurityQuestionRepository;
import com.deltacode.kcb.service.SecurityQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

@Service
public class SecurityQuestionServiceImpl implements SecurityQuestionService {
    @Autowired
    private SecurityQuestionRepository securityQuestionRepository;

    @Override
    public ResponseEntity<?> getAllSecurityQuestions() {
        LinkedHashMap<String, Object> responseParams = new LinkedHashMap<>();
        LinkedHashMap<String, Object> responseObject = new LinkedHashMap<>();
        try {
            responseObject.put("securityQuestions", securityQuestionRepository.findAll());
            responseParams.put("status", 1);
            responseParams.put("message", "Security questions retrieved successfully");
            responseParams.put("data", responseObject);
            return ResponseEntity.ok(responseParams);
        } catch (Exception e) {
            responseParams.put("status", 0);
            responseParams.put("message", "Error retrieving security questions");
            responseParams.put("data", responseObject);
            return ResponseEntity.badRequest().body(responseParams);
        }

    }

    @Override
    public ResponseEntity<?> createSecurityQuestion(SecurityQuestionRequest securityQuestionRequest) {
        LinkedHashMap<String,Object>responseObject=new LinkedHashMap<>();
        try {
            if (securityQuestionRequest==null) throw new RuntimeException("Security question request is null");
            SecurityQuestion securityQuestion=new SecurityQuestion();
            securityQuestion.setQuestion(securityQuestionRequest.getQuestion());
            securityQuestionRepository.save(securityQuestion);
            responseObject.put("status",1);
            responseObject.put("message","Security question created successfully");
            return ResponseEntity.ok(responseObject);

        } catch (RuntimeException e) {
            responseObject.put("status",0);
            responseObject.put("message",e.getMessage());
            return ResponseEntity.badRequest().body(responseObject);
        }
    }
}

