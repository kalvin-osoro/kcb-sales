package com.ekenya.rnd.backend.fskcb.DFSVoomaModule.datasource.entities;

import com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.entities.TargetStatus;
import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.datasource.entities.TargetType;
import com.ekenya.rnd.backend.fskcb.DSRModule.datasource.entities.DSRAccountEntity;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "dfs_vooma_targets")
@DynamicUpdate
@DynamicInsert
public class DFSVoomaTargetEntity {
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
    @Enumerated(EnumType.STRING)
    private AssignmentType assignmentType;
}
