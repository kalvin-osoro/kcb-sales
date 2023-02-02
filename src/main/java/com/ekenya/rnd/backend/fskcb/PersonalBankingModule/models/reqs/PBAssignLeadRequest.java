package com.ekenya.rnd.backend.fskcb.PersonalBankingModule.models.reqs;

import com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.entities.Priority;
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
    private Priority priority;
    private String startDate;
    private String escalatesEmail;
    private String endDate;
}
