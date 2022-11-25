package com.ekenya.rnd.backend.fskcb.AgencyBankingModule.datasource.entities;

import com.ekenya.rnd.backend.utils.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table
@Entity(name = "agency_banking_visits")
@DynamicUpdate
@DynamicInsert
public class AgencyBankingVisitEntity {
    @Id
    @GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
    private  Long id;
    //dsr id from dsr table
    private  Long dsrId;
    private String dsrName;
    private String agentName;
    private String reasonForVisit;
    private String visitDate;
    @Enumerated(EnumType.STRING)
    private Status status;
    //    private Zone zone;
    private Date createdOn;
    private Date updatedOn;
    private boolean isCompleted=false;
    //yes or no question
    private String IsAgentOutletsBranded;
    private String IsOutletHaveCorrectTarrif;
    private String IsTransactionRecorded;
    private String IsAgentCollectCashDepositAndPostLater;
    private String IsAgentHaveEnoughFloat;
    private String IsAgentActive;
    private String IsAgentInvolveInIllegalActivities;
    private String IsAgentHaveCopyOfAgentBulk;
    private String IsAgentChargeCustomerForUpfront;
    private String IsAgentMaintainedRecordsOfAccOpened;
    private String IsAgentTrainedKYC;
    private String IsAgentUsedManualReceipt;
    private String reconcilleFloatAccStatement;
    private String IsAgentDealWithForexExchange;
}
