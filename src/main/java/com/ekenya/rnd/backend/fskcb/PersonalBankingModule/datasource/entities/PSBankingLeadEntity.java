package com.ekenya.rnd.backend.fskcb.PersonalBankingModule.datasource.entities;

import com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.entities.LeadStatus;
import com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.entities.Priority;
import com.ekenya.rnd.backend.fskcb.DSRModule.datasource.entities.DSRAccountEntity;
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
    private String escalatesEmail;
    private Date assignedTime;
    private String customerName;
    private String product;
    private String email;
    private String customerAccountNumber;
    private String phoneNumber;
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
    private String outcomeOfTheVisit;
    private Date createdOn;
    @ManyToOne
    @JoinColumn(name = "dsrAccId")
    private DSRAccountEntity dsrAccountEntity;
}
