package com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.entities;

import com.ekenya.rnd.backend.fskcb.CorporateBankingModule.datasource.entities.CBRevenueLineEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "dbo_aqc_onboarding")
@DynamicUpdate
@DynamicInsert
public class AcquiringOnboardEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String clientLegalName;
    private Long dsrId;
    private String region;
    private String businessPhoneNumber;
    private String businessEmail;
    @Enumerated(EnumType.STRING)
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
    private boolean isApproved =false;

    private String bankName;
    private String branchName;
    private String accountName;
    private String dsrSalesCode;
    private Boolean isAssigned=false;
    private Integer accountNumber;
    private String accountNumberInUSD;
//    private String accountNumberInKES;
    private String feesAndCommission;
    @OneToMany(mappedBy="acquiringOnboardEntity", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<AcquiringPrincipalInfoEntity> acquiringPrincipalInfoEntityList = new ArrayList<>();
    @OneToMany(mappedBy="acquiringOnboardEntity", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<AcquiringPrincipalEntity> acquiringPrincipalEntityList = new ArrayList<>();
}
