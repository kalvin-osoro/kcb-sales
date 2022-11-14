package com.ekenya.rnd.backend.fskcb.UserManagement.services;

import com.deltacode.kcb.payload.SecurityQuestionRequest;
import com.ekenya.rnd.backend.fskcb.UserManagement.services.SecurityQuestionService;
import com.ekenya.rnd.backend.fskcb.entity.SecurityQuestion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

@Service
public class SecurityQuestionServiceImpl implements SecurityQuestionService {

    @Override
    public ResponseEntity<?> getAllSecurityQuestions() {
        return null;
    }

    @Override
    public ResponseEntity<?> createSecurityQuestion(SecurityQuestionRequest securityQuestionRequest) {
        return null;
    }
}

