package com.ekenya.rnd.backend.fskcb.AuthModule.models.reqs;

import lombok.Data;

@Data
public class ForgotPasswordRequest {

    private String staffNo;
    private int option = 0; //phone = 0, email = 1

}
