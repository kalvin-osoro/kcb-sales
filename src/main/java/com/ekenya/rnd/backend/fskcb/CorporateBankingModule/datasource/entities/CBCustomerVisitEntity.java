package com.ekenya.rnd.backend.fskcb.CorporateBankingModule.datasource.entities;

import com.ekenya.rnd.backend.utils.Status;
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
@Table(name = "cb_customer_visits")
public class CBCustomerVisitEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
    //dsr id from dsr table
    private  Long dsrId;
    private String dsrName;
    private String probality;
    @Enumerated(EnumType.STRING)
    private OpportunityStage stage;
    private String  prospect;
    private String expectedAmount;
    private String customerName;
    private String reasonForVisit;
    private String visitDate;
    @Enumerated(EnumType.STRING)
    private Status status;
    //    private Zone zone;
    private Date createdOn;
//    private Date updatedOn;
    private boolean isCompleted=false;
    //call details
    private String typeOfVisit;
    private String channel;
    private String productOffered;
    private String opportunities;
    private String region;
    private String remarks;
    private boolean staffOfOtherDepartmentPresent;
    private String timeSpent;
    private Date nextVisitDate;
    private String productInvolvement;
    private String cashManagement;
    private String tradeRepresentation;
    private String custodyRepresentation;
    private String snrCallRep;
    private String CVPRep;
    private String bancaRep;
    private String treasuryRep;
    private String periodic;
}
