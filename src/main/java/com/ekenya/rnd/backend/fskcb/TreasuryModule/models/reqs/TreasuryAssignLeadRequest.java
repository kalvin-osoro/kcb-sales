package com.ekenya.rnd.backend.fskcb.TreasuryModule.models.reqs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TreasuryAssignLeadRequest {
    private Long leadId;

    private Long dsrId;
    private String startDate;
    private  String endDate;
}
