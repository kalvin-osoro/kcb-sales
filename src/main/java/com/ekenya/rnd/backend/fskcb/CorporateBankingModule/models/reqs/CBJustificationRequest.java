package com.ekenya.rnd.backend.fskcb.CorporateBankingModule.models.reqs;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CBJustificationRequest {
    private String justification;
    private String monitoringMechanism;
    private String stakeholder;
}
