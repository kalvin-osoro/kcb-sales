package com.ekenya.rnd.backend.fskcb.DSRModule.models.reqs;

import com.ekenya.rnd.backend.utils.Status;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AddTeamRequest {
    private Long id;
    private String teamName;
    private String profileCode;
    private String teamLocation;
    private Status status;
    private long zoneId;
}
