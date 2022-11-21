package com.ekenya.rnd.backend.fskcb.RetailModule.models.reqs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RetailAssignLeadRequest {
    private Long leadId;

    private Long dsrId;
    private String startDate;
    private  String endDate;
}
