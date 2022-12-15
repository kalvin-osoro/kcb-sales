package com.ekenya.rnd.backend.fskcb.DFSVoomaModule.models.reqs;

import com.ekenya.rnd.backend.fskcb.entity.Zone;
import com.ekenya.rnd.backend.utils.Status;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Getter
@Setter
public class VoomaCustomerVisitsRequest {
    private  Long id;
    private  Long dsrId;
    private String dsrName;
//    private String merchantName;
    private String customerName;
    private String reasonForVisit;
    private String actionPlan;
    private String highlights;
    private String visitDate;
//    private Status status;
    private Date createdOn;
    private Date updatedOn;
    private String region;
    private String location;

    private  String longitude;
    private String latitude;
    private String branch;
    private String visitType;
    private String attendance;
    private String entityBrief;
}
