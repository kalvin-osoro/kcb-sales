package com.ekenya.rnd.backend.fskcb.AuthModule.models.reqs;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ForgotPasswordRequest {

    private String staffNo;
    private int option = 0; //phone = 0, email = 1

}
