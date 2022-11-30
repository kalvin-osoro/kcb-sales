package com.ekenya.rnd.backend.fskcb.TreasuryModule.models.reqs;

import com.ekenya.rnd.backend.fskcb.TreasuryModule.datasource.entities.CustomerType;
import com.ekenya.rnd.backend.fskcb.TreasuryModule.datasource.entities.TreasuryPriority;
import com.ekenya.rnd.backend.fskcb.TreasuryModule.datasource.entities.TreasuryStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Date;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TreasuryTradeRequest {
    private String customerName;
    private String customerID;
    private Long dsrId;
    private Long refCode;
    private Double amount;
    private String currency;
    private TreasuryPriority treasuryPriority;  //enum
    private String salesCode;
    private TreasuryStatus status;  //enum
    private String dateBooked;
    private String remarks;
    private Date createdOn;
    private boolean isApproved=false;
    private String methodOfTransaction;
    private String branchName;
    private String natureOfTheBusiness;
    private String customerSegment;
    private String monthlyTurnover;
    private String dealingFrequency;
    private String period;
    private CustomerType customerType;
}
