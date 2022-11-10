package com.ekenya.rnd.backend.fskcb.AgencyBankingModule.portalcontrollers;

import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.models.AgencyLeadRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AgencyLeadsVC {
    @PostMapping("/create-agency-lead")
    public ResponseEntity<?> createLead(@RequestBody AgencyLeadRequest leadRequest ) {
        return null;
    }
    @RequestMapping(value = "/get-all-agency-leads", method = RequestMethod.GET)
    public ResponseEntity<?> getAllLeads() {
        return null;
    }

}
