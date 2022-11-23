package com.ekenya.rnd.backend.fskcb.UserManagement.models.reps;

import lombok.Data;

@Data
public class UpdateUserProfilesRequest {

    private String staffNo;

    private long[] profiles;
}
