package com.ekenya.rnd.backend.fskcb.DSRModule.datasource.entities;

import com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.entities.AcquiringTargetEntity;
import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.datasource.entities.AgencyBankingTargetEntity;
import com.ekenya.rnd.backend.fskcb.CorporateBankingModule.datasource.entities.CBTargetEntity;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.datasource.entities.DFSVoomaTargetEntity;
import com.ekenya.rnd.backend.fskcb.PersonalBankingModule.datasource.entities.PSBankingTargetEntity;
import com.ekenya.rnd.backend.fskcb.PremiumSegmentModule.datasource.entity.PSTargetEntity;
import com.ekenya.rnd.backend.fskcb.TreasuryModule.datasource.entities.TreasuryTargetEntity;
import com.ekenya.rnd.backend.fskcb.UserManagement.datasource.entities.UserRoleEntity;
import com.ekenya.rnd.backend.fskcb.entity.Zone;
import com.ekenya.rnd.backend.utils.Status;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@DynamicInsert
@DynamicUpdate
@Table(name = "dbo_dsr_teams")
public class DSRTeamEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String code;

    private String location;

    @Column(name="created_by")
    private String createdBy;

    @Column(name="status")
    //@Enumerated(EnumType.STRING)
    private Status status= Status.ACTIVE;

    @Column(name="created_on")
    private Date createdOn = Calendar.getInstance().getTime();

    @Column(name="updated_on")
    private Date updatedOn;

    @Column(name="updated_by")
    private String updatedBy;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "zone_id",nullable = false)
//    private Zone zone;
    private Long regionId;


    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinTable(name = "dbo_team_members",
            joinColumns = @JoinColumn(name = "team_id",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "member_id"))
    private Set<DSRAccountEntity> members;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "dfs_vooma_target_team",
            joinColumns = @JoinColumn(name = "teamId"),
            inverseJoinColumns = @JoinColumn(name = "dfsVoomaTargetId"))
    private Set<DFSVoomaTargetEntity> dfsVoomaTargetEntities;

    private String campaignTargetValue;
    private String leadsTargetValue;
    private String visitsTargetValue;
    private String onboardTargetValue;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "acquiring_target_team",
            joinColumns = @JoinColumn(name = "teamId"),
            inverseJoinColumns = @JoinColumn(name = "acquiringTargetId"))
    private Set<AcquiringTargetEntity> acquiringTargetEntities;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "agency_target_team",
            joinColumns = @JoinColumn(name = "teamId"),
            inverseJoinColumns = @JoinColumn(name = "agencyTargetId"))
    private Set<AgencyBankingTargetEntity> agencyBankingTargetEntities;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "ps_banking_target_team",
            joinColumns = @JoinColumn(name = "teamId"),
            inverseJoinColumns = @JoinColumn(name = "psBankingTargetId"))
    private Set<PSBankingTargetEntity> psBankingTargetEntities;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "premium_banking_target_team",
            joinColumns = @JoinColumn(name = "teamId"),
            inverseJoinColumns = @JoinColumn(name = "premiumBankingTargetId"))
    private Set<PSTargetEntity> premiumBankingTargetEntities;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "treasury_target_team",
            joinColumns = @JoinColumn(name = "teamId"),
            inverseJoinColumns = @JoinColumn(name = "treasuryTargetId"))
    private Set<TreasuryTargetEntity> treasuryTargetEntities;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "cb_target_team",
            joinColumns = @JoinColumn(name = "teamId"),
            inverseJoinColumns = @JoinColumn(name = "cbTargetId"))
    private Set<CBTargetEntity> cbTargetEntities;

}
