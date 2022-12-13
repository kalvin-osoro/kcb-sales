package com.ekenya.rnd.backend.fskcb.CorporateBankingModule.models.reqs;

import com.ekenya.rnd.backend.fskcb.PremiumSegmentModule.datasource.entity.ConcessionStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CBConcessionRequest {
    private String customerName;
//    private String referenceNumber;
    private List<CBJustificationRequest> cbJustificationRequests;
    private List<CBRevenueLineRequest> cbRevenueLineRequests;
    private String submissionDate;
    private String submittedBy;
}
