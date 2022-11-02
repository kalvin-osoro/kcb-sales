package com.deltacode.kcb.AgencyBankingModule.controllers;

import org.springframework.http.RequestEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController(value = "/agency-banking-customer360")
public class AgencyCustomer360ViewController {
    @RequestMapping(value = "/agency-banking/customer360/{customerid}", method = RequestMethod.GET)
    public RequestEntity<?> getCustomer360View(@PathVariable long customerId){
        return null;
    }

}
