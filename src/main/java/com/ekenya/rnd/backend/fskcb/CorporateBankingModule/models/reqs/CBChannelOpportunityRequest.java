package com.ekenya.rnd.backend.fskcb.CorporateBankingModule.models.reqs;

import com.ekenya.rnd.backend.fskcb.CorporateBankingModule.datasource.entities.OpportunityStage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CBChannelOpportunityRequest {
    private String probality;
    private OpportunityStage stage;
    private String expectedAmount;
    private String opportunity;
    private Long visitId;

}
