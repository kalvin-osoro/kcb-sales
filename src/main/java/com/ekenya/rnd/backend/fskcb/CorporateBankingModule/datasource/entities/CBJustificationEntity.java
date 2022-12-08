package com.ekenya.rnd.backend.fskcb.CorporateBankingModule.datasource.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "dbo_cb_justification")
public class CBJustificationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String justification;
    private String monitoringMechanism;
    private String stakeholder;
    @ManyToOne
    @JoinColumn(name = "concessionId")
    private CBConcessionEntity cbConcessionEntity;
    private Long concessionId;


}
