package com.ekenya.rnd.backend.fskcb.DSRModule.models.reqs;

import lombok.Data;

import java.util.Date;

@Data
public class AddDSRAccountRequest {
    private String fullName;
    private String staffNo;
    private String phoneNo;
    private String salesCode;
    private Long teamId;
    private String email;
    private Date expiry;
}
