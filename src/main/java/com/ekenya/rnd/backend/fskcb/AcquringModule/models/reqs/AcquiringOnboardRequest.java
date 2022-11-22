package com.ekenya.rnd.backend.fskcb.AcquringModule.models.reqs;

import com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.entities.MerchantType;
import com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.entities.OnboardingStatus;
import com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.entities.SettlmentType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor

@JsonIgnoreProperties(ignoreUnknown = true)
public class AcquiringOnboardRequest {
    private String merchantName;
    private MerchantType merchantType;
    private String businessType;
    private String tradingName;
    private String natureOfTheBusiness;
    private String KRApin;
    private String merchantCode;
    private String region;
    private  String merchantIdNumber;
    private String merchantEmail;
    private String merchantPhone;
    private String merchantDob;
    private String merchantPbox;
    private String merchantPostalCode;
    private Long dsrId;
    private Date createdOn;
    private Boolean isApproved=false;
    private String remarks;
    private String latitude;
    private String longitude;
    private String businessName;
    //businessDetails
    private String outletPhoneNo;
    private String businessEmail;
    private String businessKRAPin;
    private boolean wantVoomaTillNumber;
    private boolean wantVoomaPaybillNumber;
    private boolean exchangeForeign;
    //payment details
    private String bank;
    private String accountName;
    private String accountNumber;
    private String branchName;
    private SettlmentType settlmentType;
    //next of kin details
    private String nextOfKinFullName;
    private String nextOfKinIdNumber;
    private String nextOfKinPhoneNumber;

    private String town;
    private String streetName;
    private String buildingName;
    private String roomNumber;
    private String county;
    private String city;
    private String nearbyLandMark;
    private OnboardingStatus status;
}
