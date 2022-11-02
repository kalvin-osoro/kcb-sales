package com.deltacode.kcb.PersonalBankingModule.controllers;

import com.deltacode.kcb.CorporateBankingModule.Payload.LeadRequest;
import org.springframework.http.RequestEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController("/personal-banking-lead")
public class PersonalBankingLeadController {
    @RequestMapping(value = "/create-personal-banking-lead", method = RequestMethod.POST)
    public RequestEntity<?> createLead(@RequestBody LeadRequest leadRequest) {
        return null;
    }
    @RequestMapping(value = "/get-all-personal-banking-leads", method = RequestMethod.GET)
    public RequestEntity<?> getAllLeads() {
        return null;
    }

}
