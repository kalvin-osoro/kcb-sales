package com.ekenya.rnd.backend.fskcb.payload;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class TownDto {
    private Long id;
    @NotEmpty(message = "Name of the Town is required")
    @Size(min = 2, message = "Name must have at least 2 characters")
    @ApiModelProperty(value = "Town name")
    private String townName;
    @ApiModelProperty(value = "Town status")
    private Boolean status;
}
