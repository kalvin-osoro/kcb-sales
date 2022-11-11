package com.ekenya.rnd.backend.fskcb.AcquringModule.portalcontrollers;

import com.ekenya.rnd.backend.fskcb.AcquringModule.models.CustomerVisitsRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AcquiringCustomerVisitsVC {

    @PostMapping("/schedule-acquiring-customer-visit")
    public ResponseEntity<?> createAsset(@RequestBody CustomerVisitsRequest assetManagementRequest) {
        return null;
    }

    @RequestMapping(value = "/get-all-acquiring-customer-visits", method = RequestMethod.GET)
    public ResponseEntity<?> getAsset() {
        return null;
    }
}
