package com.ekenya.rnd.backend.fskcb.CorporateBankingModule.datasource.entities;

import com.ekenya.rnd.backend.fskcb.PremiumSegmentModule.datasource.entity.ConcessionStatus;
import com.ekenya.rnd.backend.utils.ConcessionTrackingStatus;
import com.ekenya.rnd.backend.utils.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cb_convenants")
public class CBBankingConvenantEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String customerId;
    private String endDate;
    private String referenceNumber;
    private String condition;
    private Integer intervalForCheck;
    private String customerName;
    @Enumerated(EnumType.STRING)
    private ConcessionTrackingStatus status;

    private Date createdOn;
    private String alertMessage;
    private Integer alertBeforeExpiry;
//    @OneToOne
//    @JoinColumn(name = "concessionId")
//    private CBConcessionEntity concessionEntity;
}
