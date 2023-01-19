package com.ekenya.rnd.backend.fskcb.DFSVoomaModule.datasource.entities;

import com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.entities.AssetCondition;
import com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.entities.VisitsReport;
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
//@SQLDelete(sql = "UPDATE acquiring_asset SET is_deleted = true WHERE id = ?")
//@Where(clause = "is_deleted = false")
@Entity
@Table(name= "dfs_vooma_asset")
@DynamicInsert
@DynamicUpdate
public class DFSVoomaAssetEntity {
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
    private String agentAccNumber;
    private String visitDate;
    private String terminalId;
    private String remarks;

    private boolean assigned=false;
    //relationship of Asset to agent and merchant
  @ManyToOne
    private DFSVoomaMerchantOnboardV1 dfsVoomaOnboardEntity;
    @ManyToOne
    private DFSVoomaAgentOnboardV1 dfsVoomaAgentOnboardingEntity;

}
