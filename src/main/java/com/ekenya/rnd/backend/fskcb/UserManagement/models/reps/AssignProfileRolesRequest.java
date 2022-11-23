package com.ekenya.rnd.backend.fskcb.UserManagement.models.reps;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class AssignProfileRolesRequest {

    private long profileId;

    private List<Long> roles = new ArrayList<>();
}
