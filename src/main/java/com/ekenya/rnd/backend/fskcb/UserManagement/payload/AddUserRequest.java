package com.ekenya.rnd.backend.fskcb.UserManagement.payload;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "Sign up requirements")
public class AddUserRequest {
    @ApiModelProperty(value = "User's full name", required = true)
    private String fullName;
    @ApiModelProperty(value = "User's staff No", required = true)
    private String staffNo;
    @ApiModelProperty(value = "Email")
    private String email;
    @ApiModelProperty(value = "Password")
    private String password;

}
