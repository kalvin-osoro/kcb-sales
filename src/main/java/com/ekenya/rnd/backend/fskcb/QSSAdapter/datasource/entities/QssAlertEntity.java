package com.ekenya.rnd.backend.fskcb.QSSAdapter.datasource.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "dbo_qss_alerts")
public class QssAlertEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String senderId; //For incoming alerts

    private String receiverId; // for outgoing alerts

    private String category;

    private String title;

    private String content;

    private QssAlertStatus status;

    private QssAlertDirection type = QssAlertDirection.OUTGOING;

    private String refCode;

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date dateCreated = Calendar.getInstance().getTime();

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date timeDelivered;

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date timeRead;
}
