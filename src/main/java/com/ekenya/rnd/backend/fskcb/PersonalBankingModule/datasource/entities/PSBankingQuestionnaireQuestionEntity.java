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
@Table(name = "psbanking_questionnaire_questions")
@DynamicInsert
@DynamicUpdate
public class PSBankingQuestionnaireQuestionEntity {
    @Id
    @GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
    private Long id;
    @Lob
    @Column(name="question")
    private String question;
    @Lob
    @Column(name="description")
    private  String questionnaireDescription;
    private Date createdOn;
}
