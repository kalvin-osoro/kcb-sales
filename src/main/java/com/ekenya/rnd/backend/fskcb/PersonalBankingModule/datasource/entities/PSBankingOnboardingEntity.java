package com.ekenya.rnd.backend.fskcb.PersonalBankingModule.datasource.entities;

import com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.entities.OnboardingStatus;
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
@Table(name = "dbo_pb_onboarding")
@DynamicUpdate
@DynamicInsert
public class PSBankingOnboardingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String customerName;
    private String customerCode;
    private String region;
    private  String customerIdNumber;
    private String customerEmail;
    @Column(name="customer_phone_number")
    private String customerPhone;
    @Column(name = "customer_dob")
    private String customerDob;
    private String customerPbox;
    private String customerPostalCode;
    private Long dsrId;
    private Date createdOn;
    private Boolean isApproved=false;
    private String remarks;
    private String latitude;
    private String longitude;
    private String businessName;
    @Column(name="town")
    private String town;
    @Column(name="street_name")
    private String streetName;
    @Column(name="building_name")
    private String buildingName;
    @Column(name="room_number")
    private String roomNumber;
    @Enumerated(EnumType.STRING)
    private OnboardingStatus status;
    
}
