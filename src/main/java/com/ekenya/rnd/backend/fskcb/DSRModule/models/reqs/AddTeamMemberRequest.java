package com.ekenya.rnd.backend.fskcb.DSRModule.models.reqs;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AddTeamMemberRequest {
    private Long teamId;
    private String staffNo;
}
