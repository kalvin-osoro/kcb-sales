package com.ekenya.rnd.backend.fskcb.PersonalBankingModule.models.reqs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import java.util.Date;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PBApprovecustomerOnboarindRequest {
//    private String customerT24Account;
    private String customerT24Number;
    private String remarks;
    private String customerName;
    private String customerCode;
    private String region;
    private  String customerIdNumber;
    private String customerEmail;
    private String customerPhone;
    @Column(name = "customer_dob")
    private String customerDob;
    private String customerPbox;
    private String customerPostalCode;
    private Long dsrId;
    private Date createdOn;
    private String businessName;

}
