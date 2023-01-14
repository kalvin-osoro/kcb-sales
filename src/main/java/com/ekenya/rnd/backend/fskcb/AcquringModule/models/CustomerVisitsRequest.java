package com.ekenya.rnd.backend.fskcb.AcquringModule.models;

import com.ekenya.rnd.backend.fskcb.entity.Zone;
import com.ekenya.rnd.backend.utils.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerVisitsRequest {
    private Long dsrId;
    private String merchantName;
    private String dsrName;
    private String reasonForVisit;
    private String actionPlan;
    private String longitude;
    private String latitude;
    private String visitDate;
    private Status status;
    private Zone zone;
}
