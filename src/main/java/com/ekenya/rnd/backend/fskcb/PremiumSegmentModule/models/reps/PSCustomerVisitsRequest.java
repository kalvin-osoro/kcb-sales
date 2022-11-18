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
}
