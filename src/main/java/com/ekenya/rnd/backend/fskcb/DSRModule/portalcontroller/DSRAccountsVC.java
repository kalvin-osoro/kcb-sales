package com.ekenya.rnd.backend.fskcb.DSRModule.portalcontroller;

import com.ekenya.rnd.backend.fskcb.DSRModule.models.AddDSRAccountRequest;
import com.ekenya.rnd.backend.fskcb.DSRModule.models.ExportDSRAccountsRequest;
import com.ekenya.rnd.backend.fskcb.DSRModule.models.ImportDSRAccountsRequest;
import com.ekenya.rnd.backend.fskcb.payload.GetLeadRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class DSRAccountsVC {
    //
    @PostMapping("/dsr-accounts-create")
    public ResponseEntity<?> createAccount(@RequestBody AddDSRAccountRequest leadRequest ) {
        return null;
    }
    @RequestMapping(value = "/dsr-accounts--get-all", method = RequestMethod.GET)
    public ResponseEntity<?> getAllAccounts() {
        return null;
    }
    @PostMapping("/dsr-accounts-import")
    public ResponseEntity<?> importAccounts(@RequestBody ImportDSRAccountsRequest leadRequest ) {
        return null;
    }
    @PostMapping("/dsr-accounts-export")
    public ResponseEntity<?> exportAccounts(@RequestBody ExportDSRAccountsRequest leadRequest ) {
        return null;
    }
}
