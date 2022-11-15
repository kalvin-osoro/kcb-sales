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

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SQLDelete(sql = "UPDATE acquiring_asset SET is_deleted = true WHERE id = ?")
@Where(clause = "is_deleted = false")
@Entity
@Table(name = "acquiring_asset")
@DynamicInsert
@DynamicUpdate
public class AcquiringAssetEntity {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
   @OneToOne
    private AcqAsset assetType;
    private String serialNumber;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dsr_id")
    private DSRAccountEntity dsrDetails;
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateAssigned;
    @Column(name="status")
    @Enumerated(EnumType.STRING)
    private Status status;
    private String assetCondition;

   @Temporal(TemporalType.TIMESTAMP)
    private Date lastServiceDate;
    @Embedded
    private VisitsReport visitsReport;
 private String images;

}
