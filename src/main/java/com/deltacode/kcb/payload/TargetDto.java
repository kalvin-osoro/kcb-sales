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
    private Long id;

   @NotEmpty(message = "Target is required")
    @ApiModelProperty(value = "Target")
    private String targetName;
    @ApiModelProperty(value = "User Acc Type status")
    private Integer target;
}
