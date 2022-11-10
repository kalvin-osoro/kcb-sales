package com.deltacode.kcb.payload;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
@Setter
@Getter
@ApiModel(description = "Target object")
public class TargetDto {
    @ApiModelProperty(notes = "Target ID")
    private Long id;
    @ApiModelProperty(notes = "Target Description")
    private String description;
    @ApiModelProperty(notes = "Target Visits")
    private Visits visits;
    @ApiModelProperty(notes = "Target Campaign")
    private Campaign campaign;
    @ApiModelProperty(notes = "Target Onboarding")
    private Onboarding onboarding;

}
