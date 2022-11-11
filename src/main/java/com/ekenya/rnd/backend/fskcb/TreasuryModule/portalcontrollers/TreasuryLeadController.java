package com.ekenya.rnd.backend.fskcb.TreasuryModule.portalcontrollers;

import com.ekenya.rnd.backend.fskcb.TreasuryModule.models.TreasuryLeadRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1")
public class TreasuryLeadController {
    @PostMapping("/treasury-leads-add")
    public ResponseEntity<?> createLead(@RequestBody TreasuryLeadRequest leadRequest ) {
        return null;
    }
    @RequestMapping(value = "/treasury-get-all-leads", method = RequestMethod.GET)
    public ResponseEntity<?> getLead() {
        return null;
    }
}
