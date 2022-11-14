package com.ekenya.rnd.backend.fskcb.PersonalBankingModule.portalcontrollers;

import com.ekenya.rnd.backend.fskcb.PersonalBankingModule.models.PBLeadRequest;
import org.springframework.http.RequestEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1")
public class PBLeadsVC {
    @RequestMapping(value = "/pb-create-lead", method = RequestMethod.POST)
    public RequestEntity<?> createLead(@RequestBody PBLeadRequest leadRequest) {
        return null;
    }
    @RequestMapping(value = "/pb-get-all-leads", method = RequestMethod.GET)
    public RequestEntity<?> getAllLeads() {
        return null;
    }

}
