package com.ekenya.rnd.backend.fskcb.CorporateBankingModule.models.reqs;

import com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.entities.TargetStatus;
import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.datasource.entities.TargetType;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.datasource.entities.AssignmentType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CBAddTargetRequest {
    private String targetName;
    private String targetDesc;
    private Integer targetValue;
    private Integer targetAchievement;
    private  String targetSource;
    private Date startDate;
    private String endDate;
    private TargetType targetType;
    private Date createdOn;
    private TargetStatus targetStatus;
    private AssignmentType assignmentType;
}
