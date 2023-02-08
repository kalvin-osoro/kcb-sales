package com.ekenya.rnd.backend.fskcb.AcquringModule.models.reqs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AssignMerchant {
    private Long customerId;
//    private String dsrSalesCode;
    private Long dsrId;
    private String profileCode;
}
