package com.ekenya.rnd.backend.fskcb.CorporateBankingModule.models.reqs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CBCustomerVisitQuestionnaireRequest {
    private Long visitId;
    private Long questionId;
    private  String response;
}
