package com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.entities;

import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.datasource.entities.AssetType;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.datasource.entities.DFSVoomaMerchantOnboardV1;
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
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "acquiring_asset")
@DynamicInsert
@DynamicUpdate
public class AcquiringAssetEntity {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
 private String serialNumber;
 private Long agentId;
 private Date dateAssigned;
 @Column(name="status")
 @Enumerated(EnumType.STRING)
 private Status status= Status.ACTIVE;
 private AssetCondition assetCondition=AssetCondition.WORKING;
 private Date lastServiceDate;
 private String deviceId;
 private String location;
 private String longitude;
 private String latitude;
 @Enumerated(EnumType.STRING)
 private AssetType assetType;
 private Long assetNumber;
 private Date createdOn;
 private Long dsrId;
 private Long merchantName;
 private String merchantAccNo;
 private String visitDate;
 private String terminalId;

 private boolean assigned=false;
 //relationship of Asset to agent and merchant
 @ManyToOne
 private AcquiringOnboardEntity acquiringOnboardEntity;



}
