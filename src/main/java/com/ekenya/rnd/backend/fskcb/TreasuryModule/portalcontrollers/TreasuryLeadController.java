package com.ekenya.rnd.backend.fskcb.TreasuryModule.portalcontrollers;

import com.ekenya.rnd.backend.fskcb.TreasuryModule.models.TreasuryLeadRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("/treasury-lead")
public class TreasuryLeadController {
    @PostMapping("/add")
    public ResponseEntity<?> createLead(@RequestBody TreasuryLeadRequest leadRequest ) {
        return null;
    }
    @RequestMapping(value = "/getAllLeads", method = RequestMethod.GET)
    public ResponseEntity<?> getLead() {
        return null;
    }
}
