package com.ekenya.rnd.backend.fskcb.UserManagement.payload;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data

public class LoginRequest {
    @ApiModelProperty(value = "Login staff no")
    private String staffNo;
    @ApiModelProperty(value = "Login password")
    private String password;
}
