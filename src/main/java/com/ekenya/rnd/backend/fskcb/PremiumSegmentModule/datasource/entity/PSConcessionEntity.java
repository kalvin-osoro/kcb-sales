package com.ekenya.rnd.backend.fskcb.PremiumSegmentModule.datasource.entity;

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
@Table(name = "dbo_concession")
@DynamicInsert
@DynamicUpdate
public class PSConcessionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String customerName;
    private String submissionRate;
    private String submittedBy;
    @Embedded
    PSRevenueLineEntity PSRevenueLinesEntity;
    @Embedded
    PSJustificationEntity justifications;
    private Date createdOn;
    private Status status;

}
