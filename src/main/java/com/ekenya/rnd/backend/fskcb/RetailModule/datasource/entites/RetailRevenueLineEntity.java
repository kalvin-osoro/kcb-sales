package com.ekenya.rnd.backend.fskcb.RetailModule.datasource.entites;

import com.ekenya.rnd.backend.fskcb.CorporateBankingModule.datasource.entities.CBConcessionEntity;
import com.ekenya.rnd.backend.fskcb.CorporateBankingModule.datasource.entities.RevenueLineType;
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
@Table(name = "dbo_retail_revenue_line")
@DynamicInsert
@DynamicUpdate
public class RetailRevenueLineEntity {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private  Long id;
    private String SSRrate;
    private String recommendedRate;
    private Integer baseAmount;
    private String duration;
    private String foreignRevenue;
    private String forgoneRevenue;
    @ManyToOne
    @JoinColumn(name = "concessionId")
    private RetailConcessionEntity retailConcessionEntity;
    @Enumerated( EnumType.STRING)
    private RevenueLineType revenueLineType;
}
