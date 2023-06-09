package com.ekenya.rnd.backend.fskcb.DFSVoomaModule.datasource.entities;

import com.ekenya.rnd.backend.utils.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "dbo_questionnaire")
@DynamicUpdate
@DynamicInsert
public class QuestionnaireEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String questionnaireTitle;
    @Enumerated(EnumType.STRING)
    private QuestionnaireType questionnaireType;
    @Enumerated(EnumType.STRING)
    private Status status=Status.ACTIVE;
    private String questionnaireDesc;
    private String profileCode;

    private String duration;
    private Boolean feedbackAssigned=false;
    private String priority;
    private Date assignedOn;
    private String salesPersonName;
    @OneToMany(mappedBy="questionnaireEntity", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<QuestionEntity> questionEntitySet = new ArrayList<>();
    private Date createdOn;


}
