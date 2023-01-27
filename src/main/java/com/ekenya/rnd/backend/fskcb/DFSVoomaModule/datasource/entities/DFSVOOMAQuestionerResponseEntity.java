package com.ekenya.rnd.backend.fskcb.DFSVoomaModule.datasource.entities;

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
@Table(name = "dfs_vooma_questionnaire_responses")
@DynamicInsert
@DynamicUpdate
public class DFSVOOMAQuestionerResponseEntity {
    @Id
    @GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
    private Long id;
    private  String response;
    private  String question;
    private String customerName;
    private String nationalId;
    private String accountNo;
    private String comment;
    private Date createdOn;
    @ManyToOne
    QuestionEntity questionEntity;
    private Long questionId;
    private Long questionnaireId;
}
