package com.ekenya.rnd.backend.fskcb.CorporateBankingModule.models.reqs;

import com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.entities.LeadStatus;
import com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.entities.Priority;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
@Getter
@Setter
public class CBAddLeadRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Integer customerId;
    private String businessUnit;
    private String customerName;
    private String remarks;
    private String customerAccountNumber;
    private String topic;
    private Priority priority;
    private Long dsrId;
    private String dsrName;
    private LeadStatus leadStatus;
    private String startDate;
    private String endDate;
    private Date createdOn;
}
