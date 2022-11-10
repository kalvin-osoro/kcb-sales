package com.ekenya.rnd.backend.fskcb.AgencyBankingModule.channelcontrollers;

import org.springframework.http.RequestEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController(value = "/agency")
public class AgencyCustomer360VC {
    @RequestMapping(value = "/customer360/{customerid}", method = RequestMethod.GET)
    public RequestEntity<?> getCustomer360View(@PathVariable long customerId){

        return null;
    }

}
