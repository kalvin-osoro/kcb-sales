package com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

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
    private String merchantName;
    @Enumerated(EnumType.STRING)
    private MerchantType merchantType;
    private String businessType;
    private String tradingName;
    private String natureOfTheBusiness;
    private String KRApin;
    private String merchantCode;
    private String region;
    private  String merchantIdNumber;
    private String merchantEmail;
    @Column(name="merchant_phone_number")
    private String merchantPhone;
    @Column(name = "merchant_dob")
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
    private boolean wantVoomaTillNumber=false;
    private boolean wantVoomaPaybillNumber=false;
    private boolean exchangeForeign=false;
    //payment details
    private String bank;
    private String accountName;
    private String accountNumber;
    private String branchName;
    @Enumerated(EnumType.STRING)
    private SettlmentType settlmentType;
    //next of kin details
    private String nextOfKinFullName;
    private String nextOfKinIdNumber;
    private String nextOfKinPhoneNumber;

    @Column(name="town")
    private String town;
    @Column(name="street_name")
    private String streetName;
    @Column(name="building_name")
    private String buildingName;
    @Column(name="room_number")
    private String roomNumber;
    private String county;
    private String city;
    private String nearbyLandMark;
    @Enumerated(EnumType.STRING)
    private OnboardingStatus status;

}
