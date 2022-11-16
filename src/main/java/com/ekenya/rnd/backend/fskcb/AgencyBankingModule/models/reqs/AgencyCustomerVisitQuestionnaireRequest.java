package com.ekenya.rnd.backend.fskcb.AgencyBankingModule.models.reqs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AgencyCustomerVisitQuestionnaireRequest {
    private Long visitId;
    private Long questionId;
    private  String response;
}
