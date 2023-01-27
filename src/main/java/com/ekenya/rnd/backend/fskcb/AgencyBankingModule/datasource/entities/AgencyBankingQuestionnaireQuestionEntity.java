package com.ekenya.rnd.backend.fskcb.AgencyBankingModule.datasource.entities;

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
@Table(name = "agency_banking_questionnaire_questions")
public class AgencyBankingQuestionnaireQuestionEntity {
    @Id
    @GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
    private Long id;
    @Lob
    @Column(name="question")
    private String question;
    @Lob
    @Column(name="description")
    private  String questionnaireDescription;
    @Enumerated(EnumType.STRING)
    private BusinessUnit businessUnit;
    @Enumerated(EnumType.STRING)
    private QuestionType questionType;
    private Date createdOn;
}
