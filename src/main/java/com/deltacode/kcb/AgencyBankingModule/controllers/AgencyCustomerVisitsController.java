package com.deltacode.kcb.AgencyBankingModule.controllers;

import org.springframework.http.RequestEntity;
import org.springframework.web.bind.annotation.*;

@RestController(value = "/agency-visits")
public class AgencyCustomerVisitsController {
    @PostMapping(value = "agency-customer-visits/create")
    public RequestEntity<?>createVisit(){
        return null;
    }
    @GetMapping(value = "-agency-customer-visits/get-all-visits")
    public RequestEntity<?>getAllVisits(){
        return null;
    }
    @GetMapping(value = "agency-customer-visits/get-all-visits-by-customer-id/{customerid}")
    public RequestEntity<?>getAllVisitsByCustomerId(@PathVariable long customerId){
        return null;
    }

}
