package com.ekenya.rnd.backend.fskcb.DFSVoomaModule.portalcontroller;

import org.springframework.http.RequestEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class VoomaCustomerVisitsVC {
    @PostMapping(value = "/vooma-create-customer-visits")
    public RequestEntity<?>createVisit(){
        return null;
    }
    @GetMapping(value = "/vooma-get-all-customer-visits")
    public RequestEntity<?>getAllVisits(){
        return null;
    }
    @GetMapping(value = "/vooma-get-customer-visits-by-id/{customerid}")
    public RequestEntity<?>getAllVisitsByCustomerId(@PathVariable long customerId){
        return null;
    }

}
