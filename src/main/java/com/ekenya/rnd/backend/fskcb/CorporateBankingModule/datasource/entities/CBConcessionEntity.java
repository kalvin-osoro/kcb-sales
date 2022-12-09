package com.ekenya.rnd.backend.fskcb.CorporateBankingModule.datasource.entities;

import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.datasource.entities.QuestionEntity;
import com.ekenya.rnd.backend.fskcb.PremiumSegmentModule.datasource.entity.ConcessionStatus;
import com.ekenya.rnd.backend.fskcb.entity.Branch;
import com.ekenya.rnd.backend.utils.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.*;

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


    private Date createdOn;
    @Enumerated(EnumType.STRING)
    private ConcessionStatus concessionStatus;
    @OneToMany(mappedBy="cbConcessionEntity", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<CBRevenueLineEntity> cbRevenueLineEntityList = new ArrayList<>();
//    @OneToMany(mappedBy="cbConcession", cascade = CascadeType.ALL,orphanRemoval = true)
//    private List<CBJustificationEntity> cbJustificationEntityList = new ArrayList<>();



}
