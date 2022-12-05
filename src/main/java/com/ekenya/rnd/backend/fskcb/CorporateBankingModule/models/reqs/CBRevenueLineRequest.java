package com.ekenya.rnd.backend.fskcb.CorporateBankingModule.models.reqs;

import com.ekenya.rnd.backend.fskcb.CorporateBankingModule.datasource.entities.RevenueLineType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CBRevenueLineRequest {
    private String SSRrate;
    private String recommendedRate;
    private Integer baseAmount;
    private String duration;
    private String foreignRevenue;
    private Long concessionId;
    private String forgoneRevenue;
    private RevenueLineType revenueLineType;

}
