package com.ekenya.rnd.backend.fskcb.DFSVoomaModule.datasource.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "dbo_question")
@DynamicInsert
@DynamicUpdate
public class QuestionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
    private String question;
    private String questionType;
    @ManyToOne
    @JoinColumn(name = "questionnaireId")
    private QuestionnaireEntity questionnaireEntity;

    @OneToMany(mappedBy="questionEntity", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<DFSVOOMAQuestionerResponseEntity> responseEntities = new ArrayList<>();
}
