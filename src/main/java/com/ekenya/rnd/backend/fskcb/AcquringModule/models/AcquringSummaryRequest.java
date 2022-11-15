package com.ekenya.rnd.backend.fskcb.AcquringModule.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AcquringSummaryRequest {

    private String regionFilter;
    private String teamFilter;
    private String dateRangeFilter;
    private String statusFilter;
    private Integer daysFilter;
}
