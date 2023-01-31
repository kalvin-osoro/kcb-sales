package com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.entities;

import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.datasource.entities.AssetType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import reactor.util.annotation.Nullable;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "dbo_asset_logs")
@DynamicUpdate
@DynamicInsert
public class AssetLogsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private AssetType assetType;
    @Column(name = "dateAssigned",nullable = true)
    private Date dateAssigned;
    @Column(name = "dateCollected",nullable = true)
    private Date dateCollected;
    private Long assetNumber;
     private Date createdOn;
     private String serialNumber;
     private String customerIdNumber;
     private String action;
     private String remarks;
     private Boolean assigned=false;
     private String profileCode;

//     @Enumerated(EnumType.STRING)
     @Column(name = "assetCondition")
     private AssetCondition condition=AssetCondition.WORKING;

     private Integer customerAccNumber;

     private String dsrSalesCode;
}
