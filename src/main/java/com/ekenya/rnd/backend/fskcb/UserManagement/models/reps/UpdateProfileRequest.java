package com.ekenya.rnd.backend.fskcb.UserManagement.models.reps;

import lombok.Data;

@Data
public class UpdateProfileRequest {

    private long id;
    private String name;
    private String code;
    private String desc;
}
