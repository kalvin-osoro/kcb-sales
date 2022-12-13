package com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "dbo_aqc_onboarding_principal_info")
@DynamicUpdate
@DynamicInsert
public class AcquiringPrincipalInfoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nameOfDirectorOrPrincipalOrPartner;

    private String directorOrPrincipalOrPartnerPhoneNumber;
    private String directorOrPrincipalOrPartnerEmail;
    @ManyToOne
    @JoinColumn(name = "acquiringOnboardId")
    private AcquiringOnboardEntity acquiringOnboardEntity;
}
