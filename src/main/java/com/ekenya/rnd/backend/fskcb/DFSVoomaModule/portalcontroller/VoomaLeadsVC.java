package com.ekenya.rnd.backend.fskcb.DFSVoomaModule.portalcontroller;

import com.ekenya.rnd.backend.fskcb.payload.GetLeadRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class VoomaLeadsVC {
    @PostMapping("/vooma-create-lead")
    public ResponseEntity<?> createLead(@RequestBody GetLeadRequest leadRequest ) {
        return null;
    }
    @RequestMapping(value = "/vooma-get-all-leads", method = RequestMethod.GET)
    public ResponseEntity<?> getAllLeads() {
        return null;
    }

}
