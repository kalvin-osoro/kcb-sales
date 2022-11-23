package com.ekenya.rnd.backend.fskcb.UserManagement.models.reps;

import lombok.Data;

@Data
public class AssignRoleToUserRequest {
    private long  userId;
    private long roleId;
}
