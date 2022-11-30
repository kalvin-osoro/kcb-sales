package com.ekenya.rnd.backend.fskcb.DSRModule.models.reqs;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class EnableTeamRequest {
    private long teamId;
}
