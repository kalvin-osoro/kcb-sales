package com.ekenya.rnd.backend.fskcb.PremiumSegmentModule.portalcontrollers;

import com.ekenya.rnd.backend.fskcb.PremiumSegmentModule.models.PSLeadRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("/premium-segment-lead")
public class PSLeadsVC {
    @PostMapping("/create-premium-segment-lead")
    public ResponseEntity<?> createLead(@RequestBody PSLeadRequest leadRequest ) {
        return null;
    }
    @RequestMapping(value = "/get-premium-segment", method = RequestMethod.GET)
    public ResponseEntity<?> getLead() {
        return null;
    }
}
