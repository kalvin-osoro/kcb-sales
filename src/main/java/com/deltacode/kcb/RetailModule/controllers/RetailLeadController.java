package com.deltacode.kcb.RetailModule.controllers;//package ekenya.co.ke.frp_kcb.RetailModule.controllers;

import com.deltacode.kcb.CorporateBankingModule.Payload.LeadRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("/retail-lead")
public class RetailLeadController {
    @PostMapping("/create-retail-lead")
    public ResponseEntity<?> createLead(@RequestBody LeadRequest leadRequest ) {
        return null;
    }
    @RequestMapping(value = "/get-all--retail-lead", method = RequestMethod.GET)
    public ResponseEntity<?> getLead() {
        return null;
    }
}
