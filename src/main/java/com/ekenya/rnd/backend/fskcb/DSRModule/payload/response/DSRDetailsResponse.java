package com.ekenya.rnd.backend.fskcb.DSRModule.payload.response;

import com.ekenya.rnd.backend.fskcb.DSRModule.models.DSRTeam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DSRDetailsResponse {
    private Long id;
    private String email;
    private String mobileNo;
    private String firstName;
    private String lastName;
    private String otherName;
    private String location;
    private String gender;
    private String idNumber;
    private Long systemUserId;
    private DSRTeam dsrTeam;
    private String createdBy;
    private Date createdOn;
    private Date updatedOn;
    private String updatedBy;
    private int customerCont;
    private int merchantCount;
    private int agentCount;
    private String username;
    private String staffNo;
}
