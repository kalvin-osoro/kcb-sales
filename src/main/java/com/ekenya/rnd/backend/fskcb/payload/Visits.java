package com.ekenya.rnd.backend.fskcb.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;

@Getter
@Setter
@AllArgsConstructor
@Embeddable
public class Visits {
    private Integer visitsTarget;
    private Integer visitsAchieved;
    private Integer visitsCommission;

    public Visits() {
    }
}
