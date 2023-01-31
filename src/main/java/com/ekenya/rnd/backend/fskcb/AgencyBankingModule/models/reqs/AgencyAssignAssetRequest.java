package com.ekenya.rnd.backend.fskcb.AgencyBankingModule.models.reqs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AgencyAssignAssetRequest {
    private String serialNumber;
    private String agentIdNumber;

    private String remarks;
    private String profileCode;
}
