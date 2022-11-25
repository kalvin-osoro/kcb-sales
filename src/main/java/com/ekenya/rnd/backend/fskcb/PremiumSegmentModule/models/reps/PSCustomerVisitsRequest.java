package com.ekenya.rnd.backend.fskcb.PremiumSegmentModule.models.reps;

import com.ekenya.rnd.backend.fskcb.entity.Zone;
import com.ekenya.rnd.backend.utils.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PSCustomerVisitsRequest {
    private Long id;
    private Long dsrId;
    private String customerName;
    private String dsrName;
    private String reasonForVisit;
    private String visitDate;
    private Status status;
    private Zone zone;
    private String typeOfVisit;
    private String channel;
    private String productOffered;
    private String opportunityDiscussed;
    private String remarks;
    private String staffFromOtherDept;
    private String timeTaken;
    private String dateOfAnotherVisit;
    private String productInvolvement;
    private String cashManagementRep;
    private String tradeRep;
    private String custodyRep;
    private String snrCallRep;
    private String cvpRep;
    private String bancaRep;
    private String treasuryRep;
    private String periodicRep;
}
