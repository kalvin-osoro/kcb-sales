package com.ekenya.rnd.backend.fskcb.UserManagement.models.reps;

import lombok.Data;

@Data
public class AssignUserProfileRequest {

    private String staffNo;

    private long[] profiles;
}
