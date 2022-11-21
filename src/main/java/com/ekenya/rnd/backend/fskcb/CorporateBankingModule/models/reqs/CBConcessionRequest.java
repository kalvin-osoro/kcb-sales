package com.ekenya.rnd.backend.fskcb.CorporateBankingModule.models.reqs;

import com.ekenya.rnd.backend.fskcb.PremiumSegmentModule.datasource.entity.Justification;
import com.ekenya.rnd.backend.fskcb.PremiumSegmentModule.datasource.entity.RevenueLine;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CBConcessionRequest {
    private String customerName;
    private String submissionRate;
    private String submittedBy;
    RevenueLine revenueLines;
    Justification justifications;
}
