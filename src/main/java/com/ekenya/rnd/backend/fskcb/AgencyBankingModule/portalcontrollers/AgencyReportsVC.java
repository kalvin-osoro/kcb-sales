package com.ekenya.rnd.backend.fskcb.AgencyBankingModule.portalcontrollers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AgencyReportsVC {
    //
    @RequestMapping(value = "/get-agency-summary", method = RequestMethod.GET)
    public ResponseEntity<?> getAllLeads() {
        return null;
    }
}
