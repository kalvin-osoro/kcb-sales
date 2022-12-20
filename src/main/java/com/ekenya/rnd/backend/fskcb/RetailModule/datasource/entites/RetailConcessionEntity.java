package com.ekenya.rnd.backend.fskcb.RetailModule.datasource.entites;

import com.ekenya.rnd.backend.fskcb.CorporateBankingModule.datasource.entities.CBRevenueLineEntity;
import com.ekenya.rnd.backend.fskcb.PremiumSegmentModule.datasource.entity.ConcessionStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "dbo_retail_concession")
@DynamicInsert
@DynamicUpdate
public class RetailConcessionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String customerName;
    //    private String referenceNumber;
//    private String customerAccountNumber;
    private String submissionDate;
    private String submittedBy;
    private boolean isApproved=false;


    private Date createdOn;
    @Enumerated(EnumType.STRING)
    private ConcessionStatus concessionStatus;
    @OneToMany(mappedBy="retailConcessionEntity", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<RetailRevenueLineEntity> retailRevenueLineEntityList = new ArrayList<>();
}
