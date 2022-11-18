package com.ekenya.rnd.backend.fskcb.UserManagement.models.reps;

import lombok.Data;

@Data
public class UpdateUserProfileRequest {

    private String staffNo;

    private long[] profiles;
}
