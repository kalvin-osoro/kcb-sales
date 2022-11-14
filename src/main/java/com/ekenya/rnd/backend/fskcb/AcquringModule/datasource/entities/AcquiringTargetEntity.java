package com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.entities;

import com.ekenya.rnd.backend.fskcb.utils.Status;
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
@Table(name = "dbo_aqc_targets")

@DynamicUpdate
@DynamicInsert
public class AcquiringTargetEntity {
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
    private AquiringTargetType aquiringTargetType;
    private Date createdOn;
    @Enumerated(EnumType.STRING)
    private Status status;

}