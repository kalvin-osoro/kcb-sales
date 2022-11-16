package com.ekenya.rnd.backend.fskcb.DFSVoomaModule.models.reqs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VoomaAssignLeadRequest {
    private Long leadId;

    private Long dsrId;
    private String startDate;
    private  String endDate;
}
