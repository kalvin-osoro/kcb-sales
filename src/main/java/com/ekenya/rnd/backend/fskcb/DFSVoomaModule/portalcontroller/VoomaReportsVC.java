package com.ekenya.rnd.backend.fskcb.DFSVoomaModule.portalcontroller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api/portal/dfs/reports")
public class VoomaReportsVC {
    //TODO: implement the reports controller for all required dfs reports
    @RequestMapping(value = "/get-vooma-summary", method = RequestMethod.GET)
    public ResponseEntity<?> getSummary() {
        return null;
    }
}
