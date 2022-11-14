package com.ekenya.rnd.backend.fskcb.RetailModule.portalcontrollers;//package ekenya.co.ke.frp_kcb.RetailModule.controllers;

import com.ekenya.rnd.backend.fskcb.RetailModule.models.RetailLeadRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("/retail-lead")
public class RetailLeadsVC {
    @PostMapping("/create-retail-lead")
    public ResponseEntity<?> createLead(@RequestBody RetailLeadRequest leadRequest ) {
        return null;
    }
    @RequestMapping(value = "/get-all--retail-lead", method = RequestMethod.GET)
    public ResponseEntity<?> getLead() {
        return null;
    }
}
