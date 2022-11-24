package com.ekenya.rnd.backend.fskcb.DSRModule.models.reqs;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class UpdateTeamMembersRequest {

    private long teamId;

    private List<Long> members = new ArrayList<>();
}
