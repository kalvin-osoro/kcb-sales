package com.ekenya.rnd.backend.fskcb.UserManagement.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignupRequest {
    private String username;
    private String password;
    private String email;
    private String mobileNo;
    private String userType;
    private String firstName;
    private String lastName;
    private String otherName;
    private String staffId;
    private String location;
    private String gender;
    private String idNumber;
    private long systemUserId;


}

