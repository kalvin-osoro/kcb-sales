package com.ekenya.rnd.backend.fskcb.CorporateBankingModule.models.reqs;

import com.ekenya.rnd.backend.fskcb.CorporateBankingModule.datasource.entities.OpportunityStage;
import com.ekenya.rnd.backend.fskcb.entity.Zone;
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
public class CBCustomerVisitsRequest {
    private Long id;
    private Long dsrId;
    private String customerName;
    private String dsrName;
    private String region;
    private String reasonForVisit;
    private String visitDate;
    private Zone zone;
    //call details
    private String typeOfVisit;
    private String channel;
    private String productOffered;
    private String opportunities;
    private String remarks;
    private boolean staffOfOtherDepartmentPresent;
    private String timeSpent;
    private String probality;
    private OpportunityStage stage;
    private String  prospect;
    private String expectedAmount;
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
