package com.ekenya.rnd.backend.fskcb.CorporateBankingModule.datasource.entities;

import com.ekenya.rnd.backend.fskcb.PremiumSegmentModule.datasource.entity.ConcessionStatus;
import com.ekenya.rnd.backend.fskcb.PremiumSegmentModule.datasource.entity.PSJustificationEntity;
import com.ekenya.rnd.backend.fskcb.PremiumSegmentModule.datasource.entity.PSRevenueLineEntity;
import com.ekenya.rnd.backend.utils.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

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
    private String referenceNumber;
    private String customerAccountNumber;
    private String submissionRate;
    private String submittedBy;
    private String startDate;
    private String endDate;
    @Embedded
    PSRevenueLineEntity PSRevenueLinesEntity;
    @Embedded
    PSJustificationEntity justifications;
    private Date createdOn;
    private Status status;
    @Enumerated(EnumType.STRING)
    private ConcessionStatus concessionStatus;

}
