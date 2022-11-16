package com.ekenya.rnd.backend.fskcb.AgencyBankingModule.models.reqs;

import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.datasource.entities.AgencyTargetType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AgencyAddTargetRequest {
    private String targetDesc;
    private Integer targetValue;
    private Integer targetAchievement;
    private String targetSource;
    //    private String startDate;
//    private String endDate;
    private String targetName;
    private Date createdOn;
    private AgencyTargetType agencyTargetType;
}
