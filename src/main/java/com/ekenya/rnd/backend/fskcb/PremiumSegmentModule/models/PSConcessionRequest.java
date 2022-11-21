package com.ekenya.rnd.backend.fskcb.PremiumSegmentModule.models;

import com.ekenya.rnd.backend.fskcb.PremiumSegmentModule.datasource.entity.Justification;
import com.ekenya.rnd.backend.fskcb.PremiumSegmentModule.datasource.entity.RevenueLine;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embedded;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PSConcessionRequest {
    private String customerName;
    private String submissionRate;
    private String submittedBy;
    RevenueLine revenueLines;
    Justification justifications;
}
