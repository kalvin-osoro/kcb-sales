package com.ekenya.rnd.backend.fskcb.UserManagement.services;

import com.deltacode.kcb.payload.SecurityQuestionRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class SecurityQuestionService implements ISecurityQuestionService {

    @Override
    public ResponseEntity<?> getAllSecurityQuestions() {
        return null;
    }

    @Override
    public ResponseEntity<?> createSecurityQuestion(SecurityQuestionRequest securityQuestionRequest) {
        return null;
    }
}

