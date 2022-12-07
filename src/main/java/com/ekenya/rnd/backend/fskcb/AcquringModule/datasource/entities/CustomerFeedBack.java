package com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.entities;

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
@Table(name = "dbo_customer_feedback")
@DynamicInsert
@DynamicUpdate
public class CustomerFeedBack {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Lob
    private String describeTheService;
    @Lob
    private String whatWouldYouChange;
    @Lob
    private String whyWouldYouChange;
    @OneToOne
    private AcquiringCustomerVisitEntity acquiringCustomerVisitEntity;
    private Date createdOn;

}
