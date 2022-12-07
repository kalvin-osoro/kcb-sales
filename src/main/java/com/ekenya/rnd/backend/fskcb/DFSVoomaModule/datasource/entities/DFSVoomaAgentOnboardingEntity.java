package com.ekenya.rnd.backend.fskcb.DFSVoomaModule.datasource.entities;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "dfs_vooma_agent_onboarding")
@DynamicUpdate
@DynamicInsert
public class DFSVoomaAgentOnboardingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //outlet personal details
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

}
