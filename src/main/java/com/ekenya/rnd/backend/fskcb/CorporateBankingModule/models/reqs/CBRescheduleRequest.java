package com.ekenya.rnd.backend.fskcb.CorporateBankingModule.models.reqs;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CBRescheduleRequest {
    private Long visitId;
    private String nextVisitDate;
}
