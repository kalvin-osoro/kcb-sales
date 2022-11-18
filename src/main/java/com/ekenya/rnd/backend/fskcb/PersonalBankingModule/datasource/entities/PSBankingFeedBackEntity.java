package com.ekenya.rnd.backend.fskcb.PersonalBankingModule.datasource.entities;

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
@Table(name = "ps_banking_feedback")
@DynamicUpdate
@DynamicInsert
public class PSBankingFeedBackEntity {
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
