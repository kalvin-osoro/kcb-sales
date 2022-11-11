package com.ekenya.rnd.backend.fskcb.UserManagement.services;

import com.deltacode.kcb.payload.SecurityQuestionRequest;
import org.springframework.http.ResponseEntity;

public interface SecurityQuestionService {
    ResponseEntity<?>getAllSecurityQuestions();
    ResponseEntity<?>createSecurityQuestion(SecurityQuestionRequest securityQuestionRequest);
}
