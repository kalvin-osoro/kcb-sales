package com.ekenya.rnd.backend.fskcb.entity;

import com.ekenya.rnd.backend.fskcb.utils.Status;
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
@Table(name = "questionnaire_response")
@DynamicInsert
@DynamicUpdate
public class QuestionnaireResponse {
        @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private QuestionnaireQuestion questionnaireQuestion;

    @Column(name="account_no")
    private String accountNumber;

    @Column(name="national_id")
    private String nationalId;

    @Lob
    @Column(name="question_response")
    private String questionResponse;

    @Column(name="status")
    @Enumerated(EnumType.STRING)
    private Status status= Status.ACTIVE;

    @Column(name="created_on")
    private Date createdOn;

    @Column(name="updated_on")
    private Date updatedOn;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "updated_by")
    private Long updatedBy;
}
