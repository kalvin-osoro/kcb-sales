package com.ekenya.rnd.backend.fskcb.entity;

import com.ekenya.rnd.backend.fskcb.utils.Status;
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
@DynamicUpdate
@DynamicInsert
@Entity
@Table(name = "questions_type")
@SQLDelete(sql = "UPDATE questions_type SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
public class QuestionsType {
        @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="survey_question_type_name")
    private String name;

    @Column
    //(name="survey_question_expected_response", columnDefinition="varchar(1) default '" +
    //            "' comment 'T is answer in their own words, C is choices, B, boolean true or false'")
    private String expectedResponse;

    @Column(name="status")
    @Enumerated(EnumType.STRING)
    private Status status= Status.ACTIVE;

    @Column(name="created_on")
    private Date createdOn;

    @Column(name="updated_on")
    private Date updatedOn;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "updated_by")
    private Long updatedBy;
}
