package com.ekenya.rnd.backend.fskcb.AcquringModule.models.reqs;

import com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.entities.AcquiringOnboardEntity;
import com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.entities.MerchantType;
import com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.entities.OnboardingStatus;
import com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.entities.SettlmentType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor

@JsonIgnoreProperties(ignoreUnknown = true)
public class AcquiringOnboardRequest {
    private String clientLegalName;
    private Long dsrId;
    private String region;
    private String businessPhoneNumber;
    private String businessEmail;
    private OnboardingStatus status;
    private Date createdOn;
    private String latitude;
    private String longitude;
    private  String businessName;
    private String businessWebsite;
    private String outletContactPerson;
    private String outletPhone;
    private Integer numberOfOutlet;
    private String typeOfGoodAndServices;

    private String bankName;
    private String branchName;
    private String accountName;
    private String accountNumber;
    private String feesAndCommission;

    private String nameOfDirectorOrPrincipalOrPartner;
    private String directorOrPrincipalOrPartnerPhoneNumber;
    private String directorOrPrincipalOrPartnerEmail;
}
