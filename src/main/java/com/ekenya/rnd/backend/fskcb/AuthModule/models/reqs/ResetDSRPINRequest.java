package com.ekenya.rnd.backend.fskcb.AuthModule.models.reqs;

import lombok.Data;

@Data
public class ResetDSRPINRequest {

    private String dsrId;
    private String staffNo;
}
