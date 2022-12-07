package com.ekenya.rnd.backend.fskcb.TreasuryModule.datasource.entities;

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
@Table(name = "treasury_negotiation_request")
@DynamicUpdate
@DynamicInsert
public class TreasuryNegotiationRequestEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String customerName;
    private String customerID;
    private Double amount;
    private String currency;
    @Enumerated(EnumType.STRING)
    private TreasuryPriority priority;  //enum
    private String salesCode;
    @Enumerated(EnumType.STRING)
    private TreasuryStatus status=TreasuryStatus.OPEN;  //enum
    private String dateBooked;
    private String refCode;
    private String remarks;
    private  String phoneNumber;
    @Enumerated(EnumType.STRING)
    private NegotionCustomerType customerType;
    private boolean isApproved=false;
    private String natureOfBusiness;
    private String customerSegment;
    private String sector;
}
