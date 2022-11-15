package com.ekenya.rnd.backend.fskcb.AcquringModule.models;

import com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.entities.AquiringTargetType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AcquiringAddTargetRequest {

    private String targetDesc;
    private Integer targetValue;
    private Integer targetAchievement;
    private String targetSource;
//    private String startDate;
//    private String endDate;
    private String targetName;
    private Date createdOn;
    @Enumerated(EnumType.STRING)
    private AquiringTargetType aquiringTargetType;
}
