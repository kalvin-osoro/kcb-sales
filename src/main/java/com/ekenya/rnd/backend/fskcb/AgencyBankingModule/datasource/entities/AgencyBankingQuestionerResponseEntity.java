package com.ekenya.rnd.backend.fskcb.AgencyBankingModule.datasource.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "agency_questionnare_responses")
@DynamicInsert
@DynamicUpdate
public class AgencyBankingQuestionerResponseEntity {
    @Id
    @GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
    private Long id;
    private Long visitId;
    private Long questionId;

    @ManyToOne
    private AgencyBankingQuestionnaireQuestionEntity agencyBankingQuestionnaireQuestionEntity;
    private  String response;
    private  String accountNo;
    private  String nationalId;
    private  String customerName;
}
