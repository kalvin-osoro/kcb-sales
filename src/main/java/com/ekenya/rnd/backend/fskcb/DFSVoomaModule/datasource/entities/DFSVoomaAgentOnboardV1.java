package com.ekenya.rnd.backend.fskcb.DFSVoomaModule.datasource.entities;

import com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.entities.OnboardingStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
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

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "dfs_vooma_Agent_onboardingV1")
@DynamicUpdate
@DynamicInsert

public class DFSVoomaAgentOnboardV1 {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //organisation profile
    private String organisationName;
    //business details
    private String businessCategory;
    private String businessPhoneNumber;
    private String businessEmail;
    private Boolean isKCBAgent=false;
    private Integer numberOfOutlets;
//    private String KRAPin;
    private Integer VATNumber;
    private Boolean dealingWithForeignExchange=false;
    private Boolean isApproved=false;
    private String remarks;
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
    @Enumerated(EnumType.STRING)
    private OnboardingStatus onboardingStatus;
    private Date createdOn;

    @OneToMany(mappedBy = "dfsVoomaAgentOnboardV1")
    private List<DFSVoomaAgentOwnerDetailsEntity> dfsVoomaAgentOwnerDetailsEntityList = new ArrayList<>();
    @OneToMany(mappedBy = "dfsVoomaAgentOnboardV1")
    private List<DFSVoomaAgentContactDetailsEntity> dfsVoomaAgentContactDetailsEntityList = new ArrayList<>();

}
