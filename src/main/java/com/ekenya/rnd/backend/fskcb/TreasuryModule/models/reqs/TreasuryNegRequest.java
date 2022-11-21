package com.ekenya.rnd.backend.fskcb.TreasuryModule.models.reqs;

import com.ekenya.rnd.backend.fskcb.TreasuryModule.datasource.entities.TreasuryPriority;
import com.ekenya.rnd.backend.fskcb.TreasuryModule.datasource.entities.TreasuryStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class TreasuryNegRequest {
    private String customerName;
    private String customerID;
    private Double amount;
    private String currency;
    private TreasuryPriority priority;  //enum
    private String salesCode;
    private TreasuryStatus status;  //enum
    private String dateBooked;
    private String remarks;
    private String refCode;
    private Date createdOn;
    private boolean isApproved=false;
}
