package com.ekenya.rnd.backend.fskcb.AgencyBankingModule.models.reqs;

import com.ekenya.rnd.backend.utils.Status;
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
public class AgencyCustomerVisitsRequest {
    private Long id;
    private  Long dsrId;
    private String dsrName;
    private String agentName;
    private String reasonForVisit;
    private String visitDate;
    private Status status;
    //    private Zone zone;
    private Date createdOn;
    private Date updatedOn;

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
