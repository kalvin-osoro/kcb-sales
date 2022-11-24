package com.ekenya.rnd.backend.fskcb.AuthModule.models.reqs;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class PortalLoginRequest {

    @ApiModelProperty(value = "Login staff no")
    @NotNull
    private String staffNo;
    @ApiModelProperty(value = "Login Password")
    @NotNull
    private String password;
}
