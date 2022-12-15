package com.ekenya.rnd.backend.fskcb.DFSVoomaModule.datasource.entities;

import com.ekenya.rnd.backend.utils.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "dfsvooma_customer_visits")
public class DFSVoomaCustomerVisitEntity {
    @Id
    @GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
    private  Long id;
    //dsr id from dsr table
    private  Long dsrId;
    private String dsrName;
    private String merchantName;
    private String reasonForVisit;
    private String actionPlan;
    private String highlights;
    private String visitDate;
    private String region;
    private String location;

    private  String longitude;
    private String latitude;
    private String branch;
    @Enumerated(EnumType.STRING)
    private Status status;
    private String customerName;
    private String visitType;
    private String attendance;
    private String entityBrief;
    //    private Zone zone;
    private Date createdOn;
    private Date updatedOn;
    private boolean isCompleted=false;


}
