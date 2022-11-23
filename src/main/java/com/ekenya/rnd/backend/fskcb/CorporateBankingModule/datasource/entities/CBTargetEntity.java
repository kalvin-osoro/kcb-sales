package com.ekenya.rnd.backend.fskcb.CorporateBankingModule.datasource.entities;

import com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.entities.AquiringTargetType;
import com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.entities.TargetStatus;
import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.datasource.entities.TargetType;
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
@Table(name = "dbo_cb_targets")

@DynamicUpdate
@DynamicInsert
public class CBTargetEntity {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;
    private String targetName;
    private String targetDesc;
    private Integer targetValue;
    private Integer targetAchievement;
    private  String targetSource;
    private Date startDate;
    private String endDate;
    @Enumerated(EnumType.STRING)
    private TargetType targetType;
    private Date createdOn;
    @Enumerated(EnumType.STRING)
    private TargetStatus targetStatus;
//    @OneToMany(mappedBy = "acquiringTargetEntity", cascade = CascadeType.ALL)
//    private List<DSRAccountEntity> dsrAccountEntities;

}
