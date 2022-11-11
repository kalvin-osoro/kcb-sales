package com.ekenya.rnd.backend.fskcb.AcquringModule.portalcontrollers;

import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.models.AgencyAssetRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AcquiringAssetVC {

    @PostMapping("/create-acquiring-asset")
    public ResponseEntity<?> createAcquiringAsset(@RequestBody AgencyAssetRequest assetManagementRequest) {
        return null;
    }

    @RequestMapping(value = "/get-all-acquiring-assets", method = RequestMethod.GET)
    public ResponseEntity<?> getAcquiringAsset() {
        return null;
    }
}
