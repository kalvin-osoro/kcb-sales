package com.ekenya.rnd.backend.fskcb.PremiumSegmentModule.datasource.entity;

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
@Table(name = "ps_customer_visits")
@DynamicUpdate
@DynamicInsert
public class PSCustomerVisitEntity {
    @Id
    @GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
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
