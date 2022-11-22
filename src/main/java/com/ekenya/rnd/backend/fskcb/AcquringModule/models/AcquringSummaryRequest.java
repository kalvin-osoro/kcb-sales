package com.ekenya.rnd.backend.fskcb.AcquringModule.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AcquringSummaryRequest {

    private String regionFilter;
    private String teamFilter;
    private String dateRangeFilter;
    private Date startDate;
    private Date endDate;
    private String statusFilter;
    private Integer daysFilter;
    private Long dsrId;
}
