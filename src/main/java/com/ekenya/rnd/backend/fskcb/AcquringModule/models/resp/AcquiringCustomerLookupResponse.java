package com.ekenya.rnd.backend.fskcb.AcquringModule.models.resp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AcquiringCustomerLookupResponse {
    private String name;
    private String idNo;
    private String type;
    private String dateRegistered;
}
