package com.ekenya.rnd.backend.fskcb.PersonalBankingModule.datasource.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "psbanking_questioner_response")
@DynamicUpdate
@DynamicInsert
public class PSBankingQuestionerResponseEntity {
    @Id
    @GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
    private Long id;
    private Long visitId;
    private Long questionId;
    private  String response;
}
