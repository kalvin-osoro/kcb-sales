package com.ekenya.rnd.backend.fskcb.PremiumSegmentModule.datasource.entity;

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
@Table(name = "dbo_ps_feedback")
@DynamicUpdate
@DynamicInsert
public class PSFeedBackEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String customerId;
    private String channel;
    private String visitRef;
    private String customerName;
    private Integer noOfQuestionAsked;
    private String questionAsked;
    private String response;
    private Date createdOn;
}
