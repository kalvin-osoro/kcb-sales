package com.deltacode.kcb.DFSModule.controller;

import org.springframework.http.RequestEntity;
import org.springframework.web.bind.annotation.*;

@RestController(value = "/dfs-visits")
public class DFSCustomerVisitsController {
    @PostMapping(value = "customer-visits/create")
    public RequestEntity<?>createVisit(){
        return null;
    }
    @GetMapping(value = "customer-visits/get-all-visits")
    public RequestEntity<?>getAllVisits(){
        return null;
    }
    @GetMapping(value = "customer-visits/get-all-visits-by-customer-id/{customerid}")
    public RequestEntity<?>getAllVisitsByCustomerId(@PathVariable long customerId){
        return null;
    }

}
