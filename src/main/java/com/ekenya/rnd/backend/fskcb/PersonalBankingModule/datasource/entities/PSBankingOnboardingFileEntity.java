package com.ekenya.rnd.backend.fskcb.PersonalBankingModule.datasource.entities;

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
@Table(name = "dbo_pb_onboarding_files")
@DynamicInsert
@DynamicUpdate
public class PSBankingOnboardingFileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private PSBankingOnboardingEntity psBankingOnboardingEntity;
    private String filePath;
    private Long personId;
}
