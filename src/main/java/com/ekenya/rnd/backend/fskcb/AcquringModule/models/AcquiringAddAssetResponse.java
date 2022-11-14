package com.ekenya.rnd.backend.fskcb.AcquringModule.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class AcquiringAddAssetResponse {
    private String serialNumber;
    private String condition;
    private String lastServiceDate;
    private String visitDate;
    private String dsrId;
    private String merchantId;



}
