package com.ekenya.rnd.backend.fskcb.AuthModule.models.reqs;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Data
@Validated
public class ChannelLoginRequest {
    @ApiModelProperty(value = "Login staff no")
    @NotNull
    private String staffNo;
    @ApiModelProperty(value = "Login PIN (4 digits)")
    @NotNull
    private String pin;
    @ApiModelProperty(value = "User Locations. E.G => {'lat':x.xxx,'lng':y.yyyy}")
    @NotNull
    private JsonLatLng location;

}
