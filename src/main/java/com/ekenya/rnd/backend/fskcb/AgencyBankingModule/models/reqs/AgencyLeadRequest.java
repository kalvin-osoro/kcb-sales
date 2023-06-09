package com.ekenya.rnd.backend.fskcb.AgencyBankingModule.models.reqs;

import com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.entities.LeadStatus;
import com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.entities.Priority;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AgencyLeadRequest {
    private long id;
    private  Integer customerId;
    private String businessUnit;
    private String topic;
    private Priority priority;
    private Long dsrId;
    private String dsrName;
    private LeadStatus leadStatus;
    private boolean assigned=false;
    private String startDate;
    private String endDate;
}
