package com.ekenya.rnd.backend.fskcb.UserManagement.models.reps;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RemoveUserFromRole {

    private long userId;

    private long roleId;
}
