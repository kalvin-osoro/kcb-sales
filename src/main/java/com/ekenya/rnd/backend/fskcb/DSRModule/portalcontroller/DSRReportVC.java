package com.ekenya.rnd.backend.fskcb.DSRModule.portalcontroller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DSRReportVC {
    //
    @RequestMapping(value = "/get-dsr-summary", method = RequestMethod.GET)
    public ResponseEntity<?> getSummary() {
        return null;
    }
}
