package com.ekenya.rnd.backend.fskcb.DSRModule.models.reqs;

import com.ekenya.rnd.backend.utils.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddTeamRequest {
    private Long id;
    private String teamName;
    private String teamLocation;
    private Status status;
    private long zoneId;
}
