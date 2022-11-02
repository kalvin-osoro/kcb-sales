package com.deltacode.kcb.CorporateBankingModule.controllers;

import com.deltacode.kcb.CorporateBankingModule.Payload.LeadRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("/corporate-banking-lead")
public class CorporateBankingLeadController {
    @PostMapping("/create-corporate-banking-lead")
    public ResponseEntity<?> addLead(@RequestBody LeadRequest leadRequest ) {
        return null;
    }
    @RequestMapping(value = "/get-all-lead-corporate-banking", method = RequestMethod.GET)
    public ResponseEntity<?> getLead() {
        return null;
    }
}
