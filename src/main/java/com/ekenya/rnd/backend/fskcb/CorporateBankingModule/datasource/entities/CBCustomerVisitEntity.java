package com.ekenya.rnd.backend.fskcb.CorporateBankingModule.datasource.entities;

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
@Table(name = "cb_customer_visits")
public class CBCustomerVisitEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
    //dsr id from dsr table
    private  Long dsrId;
    private String dsrName;
    private String customerName;
    private String reasonForVisit;
    private String visitDate;
    @Enumerated(EnumType.STRING)
    private Status status;
    //    private Zone zone;
    private Date createdOn;
    private Date updatedOn;
    private boolean isCompleted=false;
}
