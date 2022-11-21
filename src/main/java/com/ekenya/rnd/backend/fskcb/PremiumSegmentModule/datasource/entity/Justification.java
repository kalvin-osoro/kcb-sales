package com.ekenya.rnd.backend.fskcb.PremiumSegmentModule.datasource.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class Justification {
    private String justification;
    private String monitoringMechanism;
    private String stakeholder;

}
