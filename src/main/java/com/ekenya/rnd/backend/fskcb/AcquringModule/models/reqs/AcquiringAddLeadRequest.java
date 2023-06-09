package com.ekenya.rnd.backend.fskcb.AcquringModule.models.reqs;

import com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.entities.LeadStatus;
import com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.entities.Priority;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Date;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class AcquiringAddLeadRequest {
    private  Integer customerId;
    private String businessUnit;
    private String remarks;
    private String customerAccountNumber;
    private String customerName;
    private String topic;
    private Priority priority;
    private Long dsrId;
    private String dsrName;
    private LeadStatus leadStatus;
    private String startDate;
    private String endDate;
    private String product;
    private String email;
    private String phoneNumber;
}
