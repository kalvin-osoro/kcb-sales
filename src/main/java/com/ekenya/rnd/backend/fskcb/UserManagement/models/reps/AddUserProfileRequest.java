package com.ekenya.rnd.backend.fskcb.UserManagement.models.reps;

import lombok.Data;

@Data
public class AddUserProfileRequest {

    private String name;
    private String code;
    private String desc;
}
