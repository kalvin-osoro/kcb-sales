package com.ekenya.rnd.backend.fskcb.AgencyBankingModule.datasource.entities;

import com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.entities.LeadStatus;
import com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.entities.Priority;
import com.ekenya.rnd.backend.fskcb.DSRModule.datasource.entities.DSRAccountEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "agency_leads")
@Entity
@DynamicUpdate
@DynamicInsert
public class AgencyBankingLeadEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private  Integer customerId;
    private String businessUnit;
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
