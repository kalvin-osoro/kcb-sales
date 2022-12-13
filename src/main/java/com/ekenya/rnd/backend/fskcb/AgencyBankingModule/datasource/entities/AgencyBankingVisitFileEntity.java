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
@Table(name = "agency_banking_visit_files")
@DynamicUpdate
@DynamicInsert
public class AgencyBankingVisitFileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "visitId")
    private AgencyBankingVisitEntity agencyBankingVisitEntity;
    private String filePath;
}
