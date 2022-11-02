package com.deltacode.kcb.PersonalBankingModule.controllers;

import org.springframework.http.RequestEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController(value = "/personal-banking-customer360")
public class PersonalBankingCustomer360ViewController {
    @RequestMapping(value = "/customer/{customerid}", method = RequestMethod.GET)
    public RequestEntity<?> getCustomer360View(@PathVariable long customerId){
        return null;
    }
    }

