package com.ekenya.rnd.backend.fskcb.PersonalBankingModule.datasource.entities;

import com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.entities.LeadStatus;
import com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.entities.Priority;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "psbanking_leads")
public class PSBankingLeadEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private  Integer customerId;
    private String businessUnit;
    private String topic;
    @Enumerated(EnumType.STRING)
    private Priority priority;
    private Long dsrId;
    private String dsrName;
    @Enumerated(EnumType.STRING)
    private LeadStatus leadStatus;
    private boolean assigned=false;
    private String startDate;
    private String endDate;
    private Date createdOn;
}
