package com.ekenya.rnd.backend.fskcb.UserManagement.models.reps;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AddUserProfileRequest {

    private String name;
    private String code;
    private String desc;
}
