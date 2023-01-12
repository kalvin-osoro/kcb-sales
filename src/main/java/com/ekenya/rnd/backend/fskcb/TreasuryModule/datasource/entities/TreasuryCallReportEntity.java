package com.ekenya.rnd.backend.fskcb.TreasuryModule.datasource.entities;

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
@Table(name = "dbo_treasury_call_report")
@DynamicInsert
@DynamicUpdate
public class TreasuryCallReportEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String supplierName;
    private String theirCustomers;
    private String frequency;
    private Long dsrId;
    private String  monthlyFx;
    private String currencyOfExpense;
    private String currencyOfRelivable;
    private String  manageRiskAndCashFlow;
    private String  agreedSalesMargin;
    private String  remarks;
}
