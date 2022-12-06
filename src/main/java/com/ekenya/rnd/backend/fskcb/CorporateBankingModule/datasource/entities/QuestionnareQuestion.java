package com.ekenya.rnd.backend.fskcb.CorporateBankingModule.datasource.entities;

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
@Table(name = "dbo_cb_question_question")
@DynamicInsert
@DynamicUpdate
public class QuestionnareQuestion {
    @Id
    @GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY )
    private Long id;
    private String question;
    @Lob
    private String questionDescription;
    @ManyToOne
    private QuestionType questionType;
    private Status status=Status.ACTIVE;
    @Lob
    private String choices;
    private Date createdOn;
}
