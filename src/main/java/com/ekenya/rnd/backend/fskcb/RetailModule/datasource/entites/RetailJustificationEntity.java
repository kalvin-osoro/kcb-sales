package com.ekenya.rnd.backend.fskcb.RetailModule.datasource.entites;

import com.ekenya.rnd.backend.fskcb.CorporateBankingModule.datasource.entities.CBConcessionEntity;
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
@Table(name = "dbo_retail_jusitification")
@DynamicInsert
@DynamicUpdate
public class RetailJustificationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String justification;
    private String monitoringMechanism;
    private String stakeholder;
    @ManyToOne
    @JoinColumn(name = "concessionId")
    private RetailConcessionEntity retailConcessionEntity;
}
