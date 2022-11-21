package com.ekenya.rnd.backend.fskcb.PremiumSegmentModule.datasource.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ps_questionnaire_questions")
@DynamicUpdate
@DynamicInsert
public class PSQuestionnaireQuestionEntity {
    @Id
    @GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
    private Long questionnaireId;
    private String question;
    private  String questionnaireDescription;
    private Date createdOn;
}
