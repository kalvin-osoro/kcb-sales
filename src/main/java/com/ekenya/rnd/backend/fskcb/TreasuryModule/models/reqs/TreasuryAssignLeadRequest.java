package com.ekenya.rnd.backend.fskcb.TreasuryModule.models.reqs;

import com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.entities.Priority;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TreasuryAssignLeadRequest {
    private Long leadId;

    private Long dsrId;
    private Priority priority;

    private String escalatesEmail;
    private String staffNo;
    private String salesCode;
    private Date assignedTime;
}
