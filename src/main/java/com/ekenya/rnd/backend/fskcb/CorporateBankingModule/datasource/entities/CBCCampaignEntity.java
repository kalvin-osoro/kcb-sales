package com.ekenya.rnd.backend.fskcb.CorporateBankingModule.datasource.entities;

import com.ekenya.rnd.backend.utils.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cb_campaigns")
@DynamicUpdate
@DynamicInsert
public class CBCCampaignEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String campaignName;
    private String campaignDesc;
    private String startDate;
    private String endDate;
    @Enumerated(EnumType.STRING)
    private CampainType campainType;
    private Integer customerTargeted;
    private Integer customerAchieved;
    private String source;
    private Date createdOn;
    private Status status;
}
