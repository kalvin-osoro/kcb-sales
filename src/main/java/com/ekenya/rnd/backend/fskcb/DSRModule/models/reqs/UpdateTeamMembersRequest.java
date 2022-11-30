package com.ekenya.rnd.backend.fskcb.DSRModule.models.reqs;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UpdateTeamMembersRequest {

    private long teamId;

    private List<Long> members = new ArrayList<>();
}
