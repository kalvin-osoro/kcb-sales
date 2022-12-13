package com.ekenya.rnd.backend.fskcb.DFSVoomaModule.models.reqs;

import com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.entities.OnboardingStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DFSVoomaAgentOnboardRequest {
    private String nameOfContractSignatory;
    private String contractSignatoryPhoneNumber;
    private String contractSignatoryEmail;
    private String keyContactName;
    private String keyContactPhoneNumber;
    private String keyContactEmail;
    private String keyFinanceContactName;
    private String keyFinanceContactPhoneNumber;
    private String keyFinanceContactEmail;
    //    //company details
    private String businessType;
    private String businessPhoneNumber;
    private String businessEmail;
    private String faxNumber;
    private boolean isKCBAgent;
    private Integer numberOfOutlets;
    private String KRAPin;
    private Integer VATNumber;
    //    //physical address
    private String postalAddress;
    private String postalCode;
    private String town;
    private String nearestLandmark;
    private String staffId;
    private String latitude;
    private String longitude;
    private OnboardingStatus status;
}
