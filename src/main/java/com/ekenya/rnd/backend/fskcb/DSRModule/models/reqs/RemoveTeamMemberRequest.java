package com.ekenya.rnd.backend.fskcb.DSRModule.models.reqs;

import lombok.Data;

@Data
public class RemoveTeamMemberRequest {

    private Long teamId;

    private String StaffNo;
}
