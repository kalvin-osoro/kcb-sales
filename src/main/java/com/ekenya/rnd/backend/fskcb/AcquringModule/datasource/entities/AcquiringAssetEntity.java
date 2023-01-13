package com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.entities;

import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.datasource.entities.AssetType;
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
 private String dateAssigned;
 @Enumerated(EnumType.STRING)
 private Status status= Status.ACTIVE;
 private AssetCondition assetCondition=AssetCondition.WORKING;
 private String lastServiceDate;
 private String deviceId;
 private String location;
 private String longitude;
 private String latitude;
 @Enumerated(EnumType.STRING)
 private AssetType assetType;
 private Long assetNumber;
 private Date createdOn;
 private Long dsrId;
 private String visitDate;
 private String merchantName;
 private String merchantAccNo;
 private boolean assigned=false;



}
