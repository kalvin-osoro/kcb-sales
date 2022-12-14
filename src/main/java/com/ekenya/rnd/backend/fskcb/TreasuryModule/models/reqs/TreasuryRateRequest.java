package com.ekenya.rnd.backend.fskcb.TreasuryModule.models.reqs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TreasuryRateRequest {
    private String currencyCode;
    private String currencyName;
    private Long currencyId;
}
