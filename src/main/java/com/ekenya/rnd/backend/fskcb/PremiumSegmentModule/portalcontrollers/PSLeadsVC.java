package com.ekenya.rnd.backend.fskcb.PremiumSegmentModule.portalcontrollers;

import com.ekenya.rnd.backend.fskcb.PremiumSegmentModule.models.PSLeadRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1")
public class PSLeadsVC {
    @PostMapping("/ps-create-lead")
    public ResponseEntity<?> createLead(@RequestBody PSLeadRequest leadRequest ) {
        return null;
    }
    @RequestMapping(value = "/ps-get-all-leads", method = RequestMethod.GET)
    public ResponseEntity<?> getAllLeads() {
        return null;
    }
}
