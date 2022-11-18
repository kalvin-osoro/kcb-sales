package com.ekenya.rnd.backend.fskcb.PremiumSegmentModule.models.reps;

import com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.entities.LeadStatus;
import com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.entities.Priority;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PSAddLeadRequest {
    private Long leadId;

    private Long dsrId;
    private String startDate;
    private  String endDate;

}
