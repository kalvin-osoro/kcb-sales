package com.deltacode.kcb.controller;

import com.deltacode.kcb.payload.SecurityQuestionRequest;
import com.ekenya.rnd.backend.fskcb.UserManagement.services.SecurityQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/security-question")
public class SecurityQuestionController {
    @Autowired
    private  SecurityQuestionService securityQuestionService;
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity<?> getAllSecurityQuestions() {
        return securityQuestionService.getAllSecurityQuestions();
    }
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity<?> createSecurityQuestion(SecurityQuestionRequest securityQuestionRequest) {
        return securityQuestionService.createSecurityQuestion(securityQuestionRequest);
    }
}
