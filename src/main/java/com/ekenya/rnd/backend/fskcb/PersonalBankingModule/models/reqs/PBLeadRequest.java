package com.ekenya.rnd.backend.fskcb.PersonalBankingModule.models.reqs;

import com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.entities.LeadStatus;
import com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.entities.Priority;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PBLeadRequest {
    private  Integer customerId;
    private String businessUnit;
    private String topic;
    private Priority priority;
    private Long dsrId;
    private Long leadId;
    private String dsrName;
    private LeadStatus leadStatus;
    private boolean assigned=false;
    private String startDate;
    private String endDate;
}
