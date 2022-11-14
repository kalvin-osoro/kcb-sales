package com.ekenya.rnd.backend.fskcb.CorporateBankingModule.portalcontrollers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CBReportController {
    //
    @RequestMapping(value = "/get-cb-summary", method = RequestMethod.GET)
    public ResponseEntity<?> getSummary() {
        return null;
    }
}
