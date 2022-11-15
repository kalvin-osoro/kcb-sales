package com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.entities;

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
@Table(name = "acquiring_questionnaire_questions")
public class AcquiringQuestionnaireQuestionEntity {
    @Id
    @GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
    private Long questionnaireId;
   @Lob
   @Column(name="question")
    private String question;
    @Lob
    @Column(name="description")
   private  String questionnaireDescription;
   private Date createdOn;
}
