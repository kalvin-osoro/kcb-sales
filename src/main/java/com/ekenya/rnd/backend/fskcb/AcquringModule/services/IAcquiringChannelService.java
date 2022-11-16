package com.ekenya.rnd.backend.fskcb.AcquringModule.services;

import com.ekenya.rnd.backend.fskcb.AcquringModule.models.resp.AcquiringCustomerLookupResponse;
import com.google.gson.JsonObject;

public interface IAcquiringChannelService {

    JsonObject findCustomerByAccNo(String accNo);
}
