package com.ekenya.rnd.backend.fskcb.UserManagement.models.reps;

import lombok.Data;

@Data
public class RemoveUserFromRole {

    private long userId;

    private long roleId;
}
