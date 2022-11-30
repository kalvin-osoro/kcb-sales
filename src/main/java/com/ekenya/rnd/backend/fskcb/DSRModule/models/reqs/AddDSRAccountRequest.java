package com.ekenya.rnd.backend.fskcb.DSRModule.models.reqs;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AddDSRAccountRequest {
    private String fullName;
    private String staffNo;
    private String phoneNo;
    private String salesCode;
    private Long teamId;
    private String email;
    private Date expiry;
    private String gender;
    //private List<Long> profiles = new ArrayList<>();
}
