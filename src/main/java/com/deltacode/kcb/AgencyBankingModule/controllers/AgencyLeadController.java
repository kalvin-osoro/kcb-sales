package com.deltacode.kcb.AgencyBankingModule.controllers;

import com.deltacode.kcb.CorporateBankingModule.Payload.LeadRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("/agency-lead")
public class AgencyLeadController {
    @PostMapping("/agency/create-lead")
    public ResponseEntity<?> createLead(@RequestBody LeadRequest leadRequest ) {
        return null;
    }
    @RequestMapping(value = "agency/get-all-dfs-leads", method = RequestMethod.GET)
    public ResponseEntity<?> getAllLeads() {
        return null;
    }

}
