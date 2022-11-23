package com.ekenya.rnd.backend.fskcb.CorporateBankingModule.models.reqs;

import com.ekenya.rnd.backend.fskcb.PremiumSegmentModule.datasource.entity.ConcessionStatus;
import com.ekenya.rnd.backend.fskcb.PremiumSegmentModule.datasource.entity.PSJustificationEntity;
import com.ekenya.rnd.backend.fskcb.PremiumSegmentModule.datasource.entity.PSRevenueLineEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CBConcessionRequest {
    private String customerName;
    private String referenceNumber;
    private String submissionRate;
    private ConcessionStatus concessionStatus;
    private String submittedBy;
    private String customerAccountNumber;
    PSRevenueLineEntity PSRevenueLinesEntity;
    PSJustificationEntity justifications;
}
