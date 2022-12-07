package com.ekenya.rnd.backend.fskcb.CorporateBankingModule.datasource.entities;

import com.ekenya.rnd.backend.fskcb.CorporateBankingModule.models.reqs.OpportunityStatus;
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
@Table(name = "dbo_cb_opportunities")
@DynamicUpdate
@DynamicInsert
public class CBOpportunitiesEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String customerName;
    private String product;
    private String value;
    @Enumerated(EnumType.STRING)
    private OpportunityStage stage;
    private String probability;
    private Date createdOn;
    @Enumerated(EnumType.STRING)
    private OpportunityStatus status;
}
