package com.deltacode.kcb.PremiumSegmentModule.controllers;

import org.springframework.http.RequestEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController("/premium-customer-360")
public class PremiumSegmentCustomer360ViewController {
    @RequestMapping(value = "/details/{customerid}", method = RequestMethod.GET)
    public RequestEntity<?> getCustomer360View(@PathVariable long customerId){
        return null;
    }
}
