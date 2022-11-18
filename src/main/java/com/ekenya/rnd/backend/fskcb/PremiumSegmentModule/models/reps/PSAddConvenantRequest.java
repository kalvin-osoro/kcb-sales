package com.ekenya.rnd.backend.fskcb.PremiumSegmentModule.models.reps;

import com.ekenya.rnd.backend.fskcb.PremiumSegmentModule.datasource.entity.ConcessionStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PSAddConvenantRequest {
    private Long id;
    private String customerId;
    private String startDate;
    private String endDate;
    private Integer intervalForCheck;
    private Long dsrId;
    private ConcessionStatus status;
    private Date createdOn;
    private String alertMessage;
    private Integer alertBeforeExpiry;
}
