package com.ekenya.rnd.backend.fskcb.CorporateBankingModule.portalcontrollers;

import com.ekenya.rnd.backend.fskcb.CorporateBankingModule.models.CBLeadRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("/corporate-banking-lead")
public class CBLeadVC {
    @PostMapping("/create-corporate-banking-lead")
    public ResponseEntity<?> addLead(@RequestBody CBLeadRequest leadRequest ) {
        return null;
    }
    @RequestMapping(value = "/get-all-lead-corporate-banking", method = RequestMethod.GET)
    public ResponseEntity<?> getLead() {
        return null;
    }
}
