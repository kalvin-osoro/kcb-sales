package com.ekenya.rnd.backend.fskcb.DFSVoomaModule.models.reqs;

import com.ekenya.rnd.backend.fskcb.entity.Zone;
import com.ekenya.rnd.backend.utils.Status;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VoomaCustomerVisitsRequest {
    private Long id;
    private Long dsrId;
    private String customerName;
    private String dsrName;
    private String reasonForVisit;
    private String visitDate;
    private Status status;
    private Zone zone;
}
