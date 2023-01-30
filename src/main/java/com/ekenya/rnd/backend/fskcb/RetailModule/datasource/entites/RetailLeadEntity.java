package com.ekenya.rnd.backend.fskcb.RetailModule.datasource.entites;

import com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.entities.LeadStatus;
import com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.entities.Priority;
import com.ekenya.rnd.backend.fskcb.DSRModule.datasource.entities.DSRAccountEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "dbo_retail_leads")
public class RetailLeadEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private  Integer customerId;
    private String businessUnit;
    private String customerName;
    private String outcomeOfTheVisit;
    private String remarks;
    private String customerAccountNumber;
    private String product;
    private String profileCode;
    private String email;
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
    private Date createdOn;
    @ManyToOne
    @JoinColumn(name = "dsrAccId")
    private DSRAccountEntity dsrAccountEntity;
}
