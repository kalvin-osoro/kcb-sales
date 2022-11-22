package com.ekenya.rnd.backend.fskcb.AuthModule.models.reqs;

import lombok.Data;

@Data
public class ValidateVerificationCodeRequest {

    private String staffNo;
    private String phoneNo;
    private String code;
}
