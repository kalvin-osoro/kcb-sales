package com.ekenya.rnd.backend.fskcb.GeneralSettingsModule.controllers;

import com.ekenya.rnd.backend.fskcb.AcquringModule.models.AcquringSummaryRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public class SettingsVC {

    @PostMapping("/settings-create-branch")
    public ResponseEntity<?> getOnboardingSummary(@RequestBody AcquringSummaryRequest filters){
        //
        return null;
    }
    @RequestMapping(value = "/settings-get-all-branches", method = RequestMethod.GET)
    public ResponseEntity<?> getAllBranches() {
        return null;
    }
}
