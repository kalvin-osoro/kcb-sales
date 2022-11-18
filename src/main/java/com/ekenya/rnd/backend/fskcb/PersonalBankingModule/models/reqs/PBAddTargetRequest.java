package com.ekenya.rnd.backend.fskcb.PersonalBankingModule.models.reqs;

import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.datasource.entities.TargetType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PBAddTargetRequest {
    private String targetDesc;
    private Integer targetValue;
    private Integer targetAchievement;
    private String targetSource;
    //    private String startDate;
//    private String endDate;
    private String targetName;
    private Date createdOn;
    private TargetType targetType;
}
