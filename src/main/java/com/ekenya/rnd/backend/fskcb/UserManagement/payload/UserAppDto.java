package com.ekenya.rnd.backend.fskcb.UserManagement.payload;

import com.ekenya.rnd.backend.fskcb.UserManagement.datasource.entities.UserRoleEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.Set;

@ApiModel(description = "User object")
@Data
public class UserAppDto  {


    private Long id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String middleName;
    private String staffId;
    private String dateOfBirth;
    private Set<UserRoleEntity> roles;

}
