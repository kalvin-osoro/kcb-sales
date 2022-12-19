package com.ekenya.rnd.backend.fskcb.CorporateBankingModule.models.reqs;

import com.ekenya.rnd.backend.fskcb.PremiumSegmentModule.datasource.entity.ConcessionStatus;
import com.ekenya.rnd.backend.utils.ConcessionTrackingStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CBAddConvenantRequest {
    private Long id;
    private String customerId;
    private String endDate;
    private Integer intervalForCheck;
    private ConcessionTrackingStatus status;
    private Date createdOn;
    private String alertMessage;
    private String referenceNumber;
    private Integer alertBeforeExpiry;
}
