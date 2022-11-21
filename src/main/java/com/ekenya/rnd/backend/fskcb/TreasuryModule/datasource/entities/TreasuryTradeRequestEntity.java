package com.ekenya.rnd.backend.fskcb.TreasuryModule.datasource.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "dbo_treasury_trade_request")
@DynamicUpdate
@DynamicInsert
public class TreasuryTradeRequestEntity {
    private Long id;
    private String customerName;
    private String customerID;
    private Long refCode;
    private Double amount;
    private String currency;
    @Enumerated(EnumType.STRING)
    private TreasuryPriority priority;  //enum
    private String salesCode;
    @Enumerated(EnumType.STRING)
    private TreasuryStatus status;  //enum
    private String dateBooked;
    private String remarks;
    private Date createdOn;
    private boolean isApproved=false;
}
