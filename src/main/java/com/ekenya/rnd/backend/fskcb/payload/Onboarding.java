package com.ekenya.rnd.backend.fskcb.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
@Getter
@Setter
@AllArgsConstructor
@Embeddable
public class Onboarding {
    private Integer onboardingTarget;
    private Integer onboardingAchieved;
    private Integer onboardingCommission;

    public Onboarding() {
    }
}
