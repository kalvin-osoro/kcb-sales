package com.ekenya.rnd.backend.fskcb.PersonalBankingModule.channelcontroller;

import org.springframework.http.RequestEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController(value = "/personal-banking-customer360")
public class PBCustomer360VC {
    @RequestMapping(value = "/customer/{customerid}", method = RequestMethod.GET)
    public RequestEntity<?> getCustomer360View(@PathVariable long customerId){
        return null;
    }
    }

