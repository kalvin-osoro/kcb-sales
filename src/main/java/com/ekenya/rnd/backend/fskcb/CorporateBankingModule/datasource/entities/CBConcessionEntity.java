package com.ekenya.rnd.backend.fskcb.CorporateBankingModule.datasource.entities;

import com.ekenya.rnd.backend.fskcb.PremiumSegmentModule.datasource.entity.ConcessionStatus;
import com.ekenya.rnd.backend.utils.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "dbo_cb_concession")
@DynamicInsert
@DynamicUpdate
public class CBConcessionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String customerName;
//    private String referenceNumber;
//    private String customerAccountNumber;
    private String submissionDate;
    private String submittedBy;
//    private String startDate;
//    private String endDate;
    private String revenue;
    private String justification;
//    private Set<CBRevenueLineEntity> cbRevenueLineEntities;

    private Date createdOn;
    private Status status=Status.ACTIVE;
    @Enumerated(EnumType.STRING)
    private ConcessionStatus concessionStatus;
    private List<CBRevenueLineEntity>cbRevenueLineEntities;
    private Set<CBJustificationEntity> cbJustificationEntities;


}
