package com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.entities;

import com.ekenya.rnd.backend.fskcb.entity.Zone;
import com.ekenya.rnd.backend.utils.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "acquiring_customer_visits")
@DynamicUpdate
@DynamicInsert
public class AcquiringCustomerVisitEntity {
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
    @Enumerated(EnumType.STRING)
    private Status status=Status.ACTIVE;
//    private Zone zone;
    private Date createdOn;
    private Date updatedOn;
    private boolean isCompleted=false;
    private String visitType;
    private String attendance;
    private String entityBrief;
    private String longitude;
    private String latitude;

    ///Fields in Call report in Acquiring -visitType,
    // attendance,
    // entityBrief,
    // objective,
    // highlights,
    // actionPoints,
    // latitude,
    // longitude


}
