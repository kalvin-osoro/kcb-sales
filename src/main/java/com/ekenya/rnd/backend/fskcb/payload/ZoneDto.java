package com.ekenya.rnd.backend.fskcb.payload;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
@ApiModel(description = "Zone Object")

public class ZoneDto {
    private Long id;
    @NotEmpty(message = "Name is required")
    @Size(min = 2, message = "Name must have at least 2 characters")
    @ApiModelProperty(value = "Zone name")
    private String zoneName;
    @NotEmpty(message = "Zone Code is required")
    @Size(min = 2, message = "Zone Code must have at least 2 characters")
    @ApiModelProperty(value = "Zone  code")
    private String zoneCode;
    @NotEmpty(message = "Zone description is required")
    @Size(min = 2, message = "Zone description must have at least 2 characters")
    @ApiModelProperty(value = "Zone  description")
    private String zoneDescription;
    @ApiModelProperty(value = "Zone status")
    private  Boolean status=true;
    private Set<TeamDto> team;


}
