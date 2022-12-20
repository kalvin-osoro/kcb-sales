package com.ekenya.rnd.backend.fskcb.RetailModule.datasource.entites;

import com.ekenya.rnd.backend.utils.ConcessionTrackingStatus;
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
@Table(name = "dbo_retail_convenant_tracking")
@DynamicInsert
@DynamicUpdate
public class RetailConvenantTrackingEntoity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String customerId;
    private String endDate;
    private String referenceNumber;
    private String convenantCondition;
    private Integer intervalForCheck;
    private String customerName;
    @Enumerated(EnumType.STRING)
    private ConcessionTrackingStatus status;

    private Date createdOn;
    private String alertMessage;
    private Integer alertBeforeExpiry;
}
