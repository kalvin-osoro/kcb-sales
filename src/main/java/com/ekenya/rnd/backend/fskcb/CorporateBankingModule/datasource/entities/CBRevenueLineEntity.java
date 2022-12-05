package com.ekenya.rnd.backend.fskcb.CorporateBankingModule.datasource.entities;

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
@Table( name = "dbo_cb_revenue_lines")
@DynamicInsert
@DynamicUpdate
public class CBRevenueLineEntity {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private  Long id;
    private String SSRrate;
    private String recommendedRate;
    private Integer baseAmount;
    private String duration;
    private String foreignRevenue;
    private Long concessionId;
    private String forgoneRevenue;
    @Enumerated( EnumType.STRING)
    private RevenueLineType revenueLineType;

}
