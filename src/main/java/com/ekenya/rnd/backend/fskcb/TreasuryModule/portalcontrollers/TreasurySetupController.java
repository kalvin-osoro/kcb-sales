package com.ekenya.rnd.backend.fskcb.TreasuryModule.portalcontrollers;

import com.ekenya.rnd.backend.fskcb.TreasuryModule.Payload.TreasuryProductRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController("/treasury-setup")
public class TreasurySetupController {
    @RequestMapping(value = "/treasury-setup", method = RequestMethod.POST)
    public ResponseEntity<?>addTreasuryProduct(@RequestBody TreasuryProductRequest treasuryProductRequest) {
        return null;
    }
    // get all treasury products
    @RequestMapping(value = "/get-all-treasury-products", method = RequestMethod.GET)
    public ResponseEntity<?>getAllTreasuryProducts() {
        return null;
    }
    // delete treasury product
    @RequestMapping(value = "/delete-treasury-product", method = RequestMethod.DELETE)
    public ResponseEntity<?>deleteTreasuryProduct(@RequestBody TreasuryProductRequest treasuryProductRequest) {
        return null;
    }
    // update treasury product
    @RequestMapping(value = "/update-treasury-product", method = RequestMethod.PUT)
    public ResponseEntity<?>updateTreasuryProduct(@RequestBody TreasuryProductRequest treasuryProductRequest) {
        return null;
    }
}
