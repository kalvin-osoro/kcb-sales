package com.ekenya.rnd.backend.fskcb.payload;

import com.ekenya.rnd.backend.fskcb.entity.EmploymentType;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class CustomerDetailsResponse {
    private Long id;
    private String surname;
    private String firstName;
    private String phoneNo;
    private String lastName;
    private Date dob;
    private String gender;
    private String companyYouWorkFor;
    private EmploymentType employmentType;
    private double income;
    private String accountOpeningPurporse;
    private String  latitude ;
    private String  longitude ;
    private Date createdOn;
    private Date updatedOn;
    private Long createdBy;
    private Long updatedBy;
    private String accountTypeName;
    List<Map<String,Object>> customerKYCList;
}
