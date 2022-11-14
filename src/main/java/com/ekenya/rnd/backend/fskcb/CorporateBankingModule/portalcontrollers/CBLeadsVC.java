package com.ekenya.rnd.backend.fskcb.CorporateBankingModule.portalcontrollers;

import com.ekenya.rnd.backend.fskcb.CorporateBankingModule.models.CBLeadRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CBLeadsVC {
    @PostMapping("/cb-create-lead")
    public ResponseEntity<?> addLead(@RequestBody CBLeadRequest leadRequest ) {
        return null;
    }
    @RequestMapping(value = "/cb-get-all-leads", method = RequestMethod.GET)
    public ResponseEntity<?> getLead() {
        return null;
    }
}
