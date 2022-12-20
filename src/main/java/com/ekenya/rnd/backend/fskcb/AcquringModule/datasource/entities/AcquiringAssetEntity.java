package com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.entities;

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
    @Column(name = "assetCondition")
    private AssetCondition assetCondition;
    private Date lastServiceDate;
    private String longitude;
    private String latitude;
    private boolean assigned=false;

    @OneToMany(mappedBy = "acquiringAssetEntity", cascade = CascadeType.ALL, orphanRemoval = true)
     private List<AcquiringAssetFilesEntity> assetFiles;


}
