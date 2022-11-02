package com.deltacode.kcb.RetailModule.controllers;

import org.springframework.http.RequestEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController("/retail-customer-360")
public class RetailCustomer360ViewController {
    @RequestMapping(value = "/customer-details/{customerid}", method = RequestMethod.GET)
    public RequestEntity<?> getCustomer360View(@PathVariable long customerId){
        return null;
    }
}
