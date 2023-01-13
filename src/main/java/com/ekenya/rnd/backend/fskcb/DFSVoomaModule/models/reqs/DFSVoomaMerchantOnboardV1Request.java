package com.ekenya.rnd.backend.fskcb.DFSVoomaModule.models.reqs;

import com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.entities.MerchantType;
import com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.entities.OnboardingStatus;
import com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.entities.SettlmentType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DFSVoomaMerchantOnboardV1Request {
    private String businessType;
    private String tradingName;
    private String natureOfBusiness;
    //business details
    private String businessName;
    private String outletPhoneNumber;
    private String businessEmailAddress;
    private String businessKraPin;
    private Boolean wantTillNumber=true;
    private Boolean wantPaybillNumber=true;
    private Boolean dealingWithForeignExchange=false;
    //settlement Details
    private String branchName;
    private String accountName;
    private Integer accountNumber;
    private String date;
    //pysical address
    private String postalAddress;
    private String postalCode;
    private String cityOrTown;
    private String latitude;
    private String longitude;
    private String nearestLandmark;
    private MerchantType merchantType;
    private SettlmentType settlmentType;
    private List<DFSVoomaOwnerDetailsRequest> dfsVoomaOwnerDetailsRequests;
    private List<DFSVoomaContactDetailsRequest> dfsVoomaContactDetailsRequests;
}
