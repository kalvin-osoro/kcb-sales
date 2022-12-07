package com.ekenya.rnd.backend.fskcb.TreasuryModule.models.reqs;

import com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.entities.LeadStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TreasuryUpdateLeadRequest {
    private Long leadId;
    private String outcomeOfTheVisit;
    private LeadStatus leadStatus;
}
