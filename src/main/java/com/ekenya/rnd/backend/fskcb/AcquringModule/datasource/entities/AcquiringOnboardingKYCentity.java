package com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.entities;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "acquiring_onboarding_kyc")
@DynamicUpdate
@DynamicInsert
public class AcquiringOnboardingKYCentity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private AcquiringOnboardEntity acquiringOnboardEntity;
    private String filePath;
    private String fileName;
    private Long merchantId;
}
