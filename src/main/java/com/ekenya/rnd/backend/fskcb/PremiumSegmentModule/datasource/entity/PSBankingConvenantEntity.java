package com.ekenya.rnd.backend.fskcb.PremiumSegmentModule.datasource.entity;

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
@Table(name = "ps_banking_convenant")
public class PSBankingConvenantEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String customerId;
    private String startDate;
    private String endDate;
    private Integer intervalForCheck;
    private Long dsrId;
    @Enumerated(EnumType.STRING)
    private ConcessionStatus status;
    private Date createdOn;
    private String alertMessage;
    private Integer alertBeforeExpiry;
}
