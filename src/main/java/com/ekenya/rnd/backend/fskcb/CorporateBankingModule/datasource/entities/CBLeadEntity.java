package com.ekenya.rnd.backend.fskcb.CorporateBankingModule.datasource.entities;

import com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.entities.LeadStatus;
import com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.entities.Priority;
import com.ekenya.rnd.backend.fskcb.DSRModule.datasource.entities.DSRAccountEntity;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "dbo_cb_leads")
@DynamicInsert
@DynamicUpdate
public class CBLeadEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private  Integer customerId;
    private String businessUnit;
    private String profileCode;
    private String remarks;
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
    private boolean escalated=false;
    private String startDate;
    private String endDate;
    private String outcomeOfTheVisit;
    private String escaleteEmail;
    private String leadValue;
    private Date assignTime;
    private Date createdOn;
    @ManyToOne
    @JoinColumn(name = "dsrAccId")
    private DSRAccountEntity dsrAccountEntity;
}
