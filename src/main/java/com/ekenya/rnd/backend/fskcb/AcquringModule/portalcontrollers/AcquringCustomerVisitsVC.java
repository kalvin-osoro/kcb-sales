package com.ekenya.rnd.backend.fskcb.AcquringModule.portalcontrollers;

import com.ekenya.rnd.backend.fskcb.AcquringModule.models.CustomerVisitsRequest;
import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.models.AgencyAssetRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AcquringCustomerVisitsVC {

    @PostMapping("/schedule-customer-visit")
    public ResponseEntity<?> createAsset(@RequestBody CustomerVisitsRequest assetManagementRequest) {
        return null;
    }

    @RequestMapping(value = "/get-all-agency-assets", method = RequestMethod.GET)
    public ResponseEntity<?> getAsset() {
        return null;
    }
}
