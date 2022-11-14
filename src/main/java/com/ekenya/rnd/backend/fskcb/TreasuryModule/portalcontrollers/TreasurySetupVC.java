package com.ekenya.rnd.backend.fskcb.TreasuryModule.portalcontrollers;

import com.ekenya.rnd.backend.fskcb.TreasuryModule.Payload.TreasuryProductRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1")
public class TreasurySetupVC {
    @RequestMapping(value = "/treasury-setup-add-product", method = RequestMethod.POST)
    public ResponseEntity<?>addTreasuryProduct(@RequestBody TreasuryProductRequest treasuryProductRequest) {
        return null;
    }
    // get all treasury products
    @RequestMapping(value = "/treasury-get-all-products", method = RequestMethod.GET)
    public ResponseEntity<?>getAllTreasuryProducts() {
        return null;
    }
    // delete treasury product
    @RequestMapping(value = "/treasury-delete-product", method = RequestMethod.DELETE)
    public ResponseEntity<?>deleteTreasuryProduct(@RequestBody TreasuryProductRequest treasuryProductRequest) {
        return null;
    }
    // update treasury product
    @RequestMapping(value = "/treasury-update-product", method = RequestMethod.PUT)
    public ResponseEntity<?>updateTreasuryProduct(@RequestBody TreasuryProductRequest treasuryProductRequest) {
        return null;
    }
}