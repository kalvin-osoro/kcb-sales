package com.deltacode.kcb.payload;

import com.deltacode.kcb.utils.Status;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;

@ApiModel(description = "Account Type object")
@Data
@Slf4j
public class AccountTypeDto  {

    private Long id;
    @NotEmpty(message = "Name of the Acc Type is required")
    @Size(min = 2, message = "Account Type Name must have at least 2 characters")
    @ApiModelProperty(value = "Acc Type  name")
    private String accountTypeName;
    @NotEmpty(message = "Account Type Code is required")
    @ApiModelProperty(value = "Acc Type code")
    private String accountTypeCode;
    @ApiModelProperty(value = "Acc Type status")
    @Enumerated(EnumType.STRING)
    private Status status= Status.ACTIVE;
    public String toJson() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(this);
        } catch (Exception ex) {
            log.info("Error is ",ex.getMessage());
        }
        return "{}";
    }


}
