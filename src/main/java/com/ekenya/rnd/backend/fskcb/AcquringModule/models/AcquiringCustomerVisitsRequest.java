package com.ekenya.rnd.backend.fskcb.AcquringModule.models;

import com.ekenya.rnd.backend.utils.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Date;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AcquiringCustomerVisitsRequest {
    private Long id;
    private  Long dsrId;
    private String dsrName;
    private String merchantName;
    private String reasonForVisit;
    private String visitDate;
    private String actionPlan;
    private String highlights;
    private Status status;
    //    private Zone zone;
    private Date createdOn;
    private Date updatedOn;
}
