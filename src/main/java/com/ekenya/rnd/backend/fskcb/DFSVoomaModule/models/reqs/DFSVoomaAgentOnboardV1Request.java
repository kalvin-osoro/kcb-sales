package com.ekenya.rnd.backend.fskcb.DFSVoomaModule.models.reqs;

import com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.entities.OnboardingStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DFSVoomaAgentOnboardV1Request {
    private String organisationName;
    //business details
    private String businessCategory;
    private String businessPhoneNumber;
    private String businessEmail;
    private Boolean isKCBAgent;
    private String numberOfOutlets;
//    private String KRAPin;
    private String VATNumber;
    private Boolean dealingWithForeignExchange;
    private String remarks;
    private OnboardingStatus onboardingStatus;
    //settlement Details
    private String branchName;
    private String accountName;
    private String accountNumber;
    private String date;
    //pysical address
    private String postalAddress;
    private String postalCode;
    private String cityOrTown;
    private String nearestLandmark;
    private String latitude;
    private String longitude;
    private List<DFSVoomaAgentOwnerDetailsRequest> dfsVoomaAgentOwnerDetailsRequests;
    private List<DFSVoomaAgentContactDetailsRequest> dfsVoomaAgentContactDetailsRequests;
}
