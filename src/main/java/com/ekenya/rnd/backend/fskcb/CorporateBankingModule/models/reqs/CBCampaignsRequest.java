package com.ekenya.rnd.backend.fskcb.CorporateBankingModule.models.reqs;

import com.ekenya.rnd.backend.fskcb.CorporateBankingModule.datasource.entities.CampainType;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CBCampaignsRequest {
    private String campaignName;
    private String campaignDesc;
    private String startDate;
    private String endDate;
    private CampainType campainType;
    private Integer customerTargeted;
    private Integer customerAchieved;
    private String source;
    private Date createdOn;
}
