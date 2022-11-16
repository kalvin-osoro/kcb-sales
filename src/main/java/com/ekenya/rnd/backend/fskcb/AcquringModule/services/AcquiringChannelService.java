package com.ekenya.rnd.backend.fskcb.AcquringModule.services;

import com.ekenya.rnd.backend.fskcb.AcquringModule.models.resp.AcquiringCustomerLookupResponse;
import com.ekenya.rnd.backend.fskcb.CrmAdapter.ICRMService;
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AcquiringChannelService implements IAcquiringChannelService{
    @Autowired
    ICRMService crmService;
    @Override
    public JsonObject findCustomerByAccNo(String accNo) {
        return null;
    }
}
