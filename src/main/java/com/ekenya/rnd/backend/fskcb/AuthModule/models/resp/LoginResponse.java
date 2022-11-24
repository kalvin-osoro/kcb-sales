package com.ekenya.rnd.backend.fskcb.AuthModule.models.resp;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class LoginResponse {

    private boolean success = false;
    private int remAttempts = 3;
    private String token;
    private String type;
    private Date issued;
    private int expiresInMinutes = 30;
    private List<String> profiles;

    private String errorMessage;
}
