package com.deltacode.kcb.DSRModule.payload.request;

import com.deltacode.kcb.utils.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DSRTeamRequest {
    private Long id;
    private String teamName;
    private String teamLocation;
    private Status status;
    private long zoneId;
}
