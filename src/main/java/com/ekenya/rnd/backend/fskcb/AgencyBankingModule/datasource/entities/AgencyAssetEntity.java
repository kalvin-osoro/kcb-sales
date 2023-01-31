package com.ekenya.rnd.backend.fskcb.AgencyBankingModule.datasource.entities;

import com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.entities.AssetCondition;
import com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.entities.VisitsReport;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.datasource.entities.AssetType;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.datasource.entities.DFSVoomaOnboardEntity;
import com.ekenya.rnd.backend.fskcb.DSRModule.datasource.entities.DSRAccountEntity;
import com.ekenya.rnd.backend.utils.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "agency_asset")
@DynamicInsert
@DynamicUpdate
public class AgencyAssetEntity {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
 private String serialNumber;
 private Long agentId;
 @Column(nullable = true)
 private Date dateAssigned;
 @Column(name="status")
 @Enumerated(EnumType.STRING)
 private Status status= Status.ACTIVE;
 private AssetCondition assetCondition=AssetCondition.WORKING;
 private Date lastServiceDate;
 private String deviceId;
 private String agentIdNumber;
 private String location;
 private String longitude;
 private String latitude;
 @Enumerated(EnumType.STRING)
 private AssetType assetType;
 private Long assetNumber;
 private Date createdOn;
 private Long dsrId;
 private String agentName;
 private String agentAccNumber;
 private String visitDate;
 private String terminalId;

 private boolean assigned=false;
 @ManyToOne
 private AgencyOnboardingEntity agencyOnboardingEntity;

}
