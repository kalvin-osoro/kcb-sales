package com.ekenya.rnd.backend.fskcb.CorporateBankingModule.models.reqs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CBApproveConcessionRequest {
    private Long concessionId;
    private String emailUrl;
}
