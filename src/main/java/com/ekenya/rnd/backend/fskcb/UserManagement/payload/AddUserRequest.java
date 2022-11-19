package com.ekenya.rnd.backend.fskcb.UserManagement.payload;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
@Validated
@ApiModel(description = "Sign up requirements")
public class AddUserRequest {
    @NotNull
    @ApiModelProperty(value = "User's full name", required = true)
    private String fullName;

    @NotNull
    @ApiModelProperty(value = "User's staff No", required = true)
    private String staffNo;

    @ApiModelProperty(value = "Email")
    @NotNull
    private String phoneNo;

    @ApiModelProperty(value = "Email")
    @Email
    private String email;

    @ApiModelProperty(value = "Password")
    @NotNull
    private String password;

}
