package com.ekenya.rnd.backend.fskcb.DSRModule.models.reqs;

import lombok.Data;

@Data
public class AddTeamMemberRequest {
    private Long teamId;
    private String staffNo;
}
