package com.ekenya.rnd.backend.fskcb.PersonalBankingModule.datasource.entities;

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
@Table(name = "psbanking_visits")
@DynamicUpdate
@DynamicInsert
public class PSBankingVisitEntity {
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
    private String channelUsed;
    private String actionplan;
    private String highlightOfVisit;
}
