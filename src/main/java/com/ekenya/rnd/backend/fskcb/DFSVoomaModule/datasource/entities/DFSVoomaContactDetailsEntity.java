package com.ekenya.rnd.backend.fskcb.DFSVoomaModule.datasource.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "dfs_vooma_contact_details")
@DynamicUpdate
@DynamicInsert
public class DFSVoomaContactDetailsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String contactPersonName;
    private String contactPersonPhoneNumber;
    private String contactPersonEmailAddress;
    private String contactPersonIdNumber;
    private String contactPersonIdType;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "merchantOnboardId")
    private DFSVoomaMerchantOnboardV1 dfsVoomaMerchantOnboardV1;

    private String financialContactPersonName;
    private String financialContactPersonPhoneNumber;
    private String financialContactPersonEmailAddress;
    private String financialContactPersonIdNumber;
    private String financialContactPersonIdType;
    private Long merchantId;
    //administrative contact
    private String administrativeContactPersonName;
    private String administrativeContactPersonPhoneNumber;
    private String administrativeContactPersonEmailAddress;
    private String administrativeContactPersonIdNumber;
    private String administrativeContactPersonIdType;
}
