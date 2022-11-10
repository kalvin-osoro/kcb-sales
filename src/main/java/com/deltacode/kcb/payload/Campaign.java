package com.deltacode.kcb.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;

@Getter
@Setter
@AllArgsConstructor
@Embeddable
public class Campaign {
    private Integer campaignTarget;
    private Integer campaignAchieved;
    private Integer campaignCommission;

    public Campaign() {
    }
}
