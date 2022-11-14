package com.ekenya.rnd.backend.fskcb.entity;

import com.ekenya.rnd.backend.utils.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
@SQLDelete(sql = "UPDATE questionnaire_question SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
@Entity
@Table(name = "questionnaire_question")
public class QuestionnaireQuestion {
        @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private UserAccType userAccountType;

    @Lob
    @Column(name="question")
    private String question;

    @Lob
    @Column(name="description")
    private String questionDescription;

    @ManyToOne
    @JoinColumn(name="questionType_id")
    private QuestionsType questionType;

    @Column(name="status")
    @Enumerated(EnumType.STRING)
    private Status status= Status.ACTIVE;

    @Lob
    @Column(name="choices") //csv
    private String choices;

    @Column(name="created_on")
    private Date createdOn;

    @Column(name="updated_on")
    private Date updatedOn;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "updated_by")
    private Long updatedBy;


}
