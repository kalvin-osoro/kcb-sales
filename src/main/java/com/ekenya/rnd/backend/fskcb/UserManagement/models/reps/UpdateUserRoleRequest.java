package com.ekenya.rnd.backend.fskcb.UserManagement.models.reps;

import lombok.Data;

@Data
public class UpdateUserRoleRequest {

    private String name;
    private long roleId;
    private String desc;
}
