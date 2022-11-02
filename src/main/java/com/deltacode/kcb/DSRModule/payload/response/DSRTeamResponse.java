package com.deltacode.kcb.DSRModule.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DSRTeamResponse {

    private Long id;

    private String teamName;
    private String teamLocation;
    private String createdBy;
    private String status;
    private Date createdOn;
    private Date updatedOn;
    private String updatedBy;
    private int teamMembersCount;
}
