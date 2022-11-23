package com.ekenya.rnd.backend.fskcb.CorporateBankingModule.models.reqs;

import lombok.Data;

@Data
public class CBCustomerVisitReportRequest {

    private String appointmentId;




    //Opportunity data
    private String opportunityId;//for existing opportunity
    private String opportunityStage;//Initiation, Negotiation, Documentation, ...
    private double opportunityAmount; //in KES
    private double probability;//0 to 100;
    private String opportunityTranslation; //Loan, Fixed Deposit, ...
}
