package com.ekenya.rnd.backend.fskcb.AuthModule.models.reqs;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import javax.xml.transform.Result;

@Data
@Validated
public class LoginRequest {
    @ApiModelProperty(value = "Login staff no")
    @NotNull
    private String staffNo;
    @ApiModelProperty(value = "Login password")
    @NotNull
    private String password;
    @ApiModelProperty(value = "User Locations {'lat':x.xxx,'lng':y.yyyy}")
    @NotNull
    private JsonLatLng location;

}
