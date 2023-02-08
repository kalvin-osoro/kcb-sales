package com.ekenya.rnd.backend.fskcb.AcquringModule.models.reqs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DSRMerchantRequest {
    private Long dsrId;
    private String profileCode;
}
