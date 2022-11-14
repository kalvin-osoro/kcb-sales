package com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.entities;

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
@Table(name = "dbo_aqc_targets_dsr")
@DynamicInsert
@DynamicUpdate
public class AcquiringDSRsInTargetEntity {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;
    private Integer setTarget;
    private Integer achievedTarget;
    private String dsrName;
    private String targetName;
    private Date createdOn;
}
