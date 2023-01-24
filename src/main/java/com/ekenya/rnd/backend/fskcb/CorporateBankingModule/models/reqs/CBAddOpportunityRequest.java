package com.ekenya.rnd.backend.fskcb.CorporateBankingModule.models.reqs;

import com.ekenya.rnd.backend.fskcb.CorporateBankingModule.datasource.entities.CBSectors;
import com.ekenya.rnd.backend.fskcb.CorporateBankingModule.datasource.entities.OpportunityStage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CBAddOpportunityRequest {
    private String customerName;
    private String product;
    private String value;
    private OpportunityStage stage;
    private CBSectors cbSectors;
    private String probability;
    private OpportunityStatus status;

}
