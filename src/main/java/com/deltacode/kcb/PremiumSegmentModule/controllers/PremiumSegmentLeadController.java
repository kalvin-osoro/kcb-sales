package com.deltacode.kcb.PremiumSegmentModule.controllers;

import com.deltacode.kcb.CorporateBankingModule.Payload.LeadRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("/premium-segment-lead")
public class PremiumSegmentLeadController {
    @PostMapping("/create-premium-segment-lead")
    public ResponseEntity<?> createLead(@RequestBody LeadRequest leadRequest ) {
        return null;
    }
    @RequestMapping(value = "/get-premium-segment", method = RequestMethod.GET)
    public ResponseEntity<?> getLead() {
        return null;
    }
}
