package com.ekenya.rnd.backend.fskcb.TreasuryModule.models.reqs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TreasuryCustomerCallReportRequest {
    private String supplierName;
    private String theirCustomers;
    private String frequency;
    private String dsrId;
    private String  monthlyFx;
    private String currencyOfExpense;
    private String currencyOfRelivable;
    private String  manageRiskAndCashFlow;
    private String  agreedSalesMargin;
    private String  remarks;
}
