package com.ekenya.rnd.backend.fskcb.AuthModule.models.reqs;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ChangeDSRPhoneNoRequest {

    private String staffNo;

    private String phoneNo;
}
