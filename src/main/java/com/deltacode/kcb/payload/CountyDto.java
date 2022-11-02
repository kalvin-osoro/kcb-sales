package com.deltacode.kcb.payload;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Set;

@ApiModel(description = "County object")
@Data
public class CountyDto  {
    private Long id;
    @NotEmpty(message = "Name of the County is required")
    @Size(min = 2, message = "Name must have at least 2 characters")
    @ApiModelProperty(value = "County name")
    private String countyName;
    @NotEmpty(message = "County code is required")
    @ApiModelProperty(value = "County code")
    private Long countyCode;
    @ApiModelProperty(value="County description")
    private String description;
    @ApiModelProperty(value = "County status")
    private boolean status=true;
    private Set<ConstituencyDto> constituency;


}
