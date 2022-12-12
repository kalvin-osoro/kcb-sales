package com.ekenya.rnd.backend.fskcb.DFSVoomaModule.datasource.entities;

import com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.entities.OnboardingStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "dbo_dfs_vooma_merchant_onboard")
@DynamicUpdate
@DynamicInsert
public class DFSVoomaMerchantOnboardV1 {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //organisation profile
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
    private String accountNumber;
    private String date;
    //pysical address
    private String postalAddress;
    private String postalCode;
    private String cityOrTown;
    private String nearestLandmark;
    private String latitude;
    private String longitude;
    private boolean isApproved=false;
    @Enumerated(EnumType.STRING)
    private OnboardingStatus onboardingStatus;
    private String remarks;
    @OneToMany(mappedBy = "dfsVoomaMerchantOnboardV1")
    private List<DFSVoomaOwnerDetailsEntity> dfsVoomaOwnerDetailsEntities=new ArrayList<>();
    @OneToMany(mappedBy = "dfsVoomaMerchantOnboardV1")
    private List<DFSVoomaContactDetailsEntity>dfsVoomaContactDetailsEntities=new ArrayList<>();
}
