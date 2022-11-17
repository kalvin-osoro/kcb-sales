package com.ekenya.rnd.backend.fskcb.AuthModule.models.reqs;

import lombok.Data;

@Data
public class ChangeDSRPhoneNoRequest {

    private String staffNo;

    private String phoneNo;
}
