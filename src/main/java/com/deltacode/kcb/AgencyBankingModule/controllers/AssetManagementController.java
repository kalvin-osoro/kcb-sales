package com.deltacode.kcb.AgencyBankingModule.controllers;

import com.deltacode.kcb.payload.AssetManagementRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("/agency-banking-assets")
public class AssetManagementController {

    @PostMapping("/create-agency-banking-asset")
    public ResponseEntity<?> createAsset(@RequestBody AssetManagementRequest assetManagementRequest) {
        return null;
    }

    @RequestMapping(value = "/get-all-agency-baking-assets", method = RequestMethod.GET)
    public ResponseEntity<?> getAsset() {
        return null;
    }
}
