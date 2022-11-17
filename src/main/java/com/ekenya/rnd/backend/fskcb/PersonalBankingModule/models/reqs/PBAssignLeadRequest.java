package com.ekenya.rnd.backend.fskcb.PersonalBankingModule.models.reqs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PBAssignLeadRequest {
    private Long leadId;
    private Long dsrId;
    private String startDate;
    private String endDate;
}
