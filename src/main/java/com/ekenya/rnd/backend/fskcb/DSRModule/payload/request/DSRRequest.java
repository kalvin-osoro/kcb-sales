package com.ekenya.rnd.backend.fskcb.DSRModule.payload.request;

import com.ekenya.rnd.backend.utils.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DSRRequest {
    private long dsrId;
    private String username;
    private String password;
    private String email;
    private String mobileNo;
    private String userType;
    private String firstName;
    private String lastName;
    private String otherName;
    private String location;
    private String gender;
    private String idNumber;
    private String staffNumber;
    private long teamId;
    private Status status;
    private long systemUserId;

}
