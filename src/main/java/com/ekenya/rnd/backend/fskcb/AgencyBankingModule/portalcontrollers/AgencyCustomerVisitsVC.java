package com.ekenya.rnd.backend.fskcb.AgencyBankingModule.portalcontrollers;

import org.springframework.http.RequestEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AgencyCustomerVisitsVC {
    @PostMapping(value = "/create-agency-customer-visit")
    public RequestEntity<?> createVisit(){
        return null;
    }
    @GetMapping(value = "/get-all-agency-customer-visits")
    public RequestEntity<?> getAllVisits(){
        return null;
    }
    @GetMapping(value = "/get-all-visits-by-customer-id/{customerid}")
    public RequestEntity<?>getAllVisitsByCustomerId(@PathVariable long customerId){
        return null;
    }

}
