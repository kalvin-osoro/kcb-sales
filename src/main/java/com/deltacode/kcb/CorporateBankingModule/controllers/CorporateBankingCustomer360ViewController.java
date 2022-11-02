package com.deltacode.kcb.CorporateBankingModule.controllers;

import io.swagger.annotations.Api;
import org.springframework.http.RequestEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController("/corporate-customer-view")
@Api(value = "corporate Banking customer 360")
public class CorporateBankingCustomer360ViewController {

    @RequestMapping(value = "/corporate-customer360/{customerid}", method = RequestMethod.GET)
    public RequestEntity<?> getCustomer360View(@PathVariable long customerId){
        return null;
    }}
