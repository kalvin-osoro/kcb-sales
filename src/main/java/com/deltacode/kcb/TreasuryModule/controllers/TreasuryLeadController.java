package com.deltacode.kcb.TreasuryModule.controllers;

import com.deltacode.kcb.CorporateBankingModule.Payload.LeadRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("/treasury-lead")
public class TreasuryLeadController {
    @PostMapping("/add")
    public ResponseEntity<?> createLead(@RequestBody LeadRequest leadRequest ) {
        return null;
    }
    @RequestMapping(value = "/getAllLeads", method = RequestMethod.GET)
    public ResponseEntity<?> getLead() {
        return null;
    }
}
