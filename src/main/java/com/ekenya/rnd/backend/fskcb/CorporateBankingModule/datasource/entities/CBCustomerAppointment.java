package com.ekenya.rnd.backend.fskcb.CorporateBankingModule.datasource.entities;

import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.datasource.entities.DFSVoomaTargetEntity;
import com.ekenya.rnd.backend.fskcb.DSRModule.datasource.entities.DSRAccountEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cb_customer_appointments")
@DynamicInsert
@DynamicUpdate
public class CBCustomerAppointment {
    @Id
    @GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
    private Long id;
    private String typeOfAppointment;
    private String appointmentDate;
    private String appointmentTime;
    private String duration;
    private String reasonForVisit;
    private String customerName;
    private String customerPhoneNumber;
    private Long dsrId;
//    @ManyToOne
//    @JoinColumn(name = "dsrAgentId")
//    private DSRAccountEntity dsrAccountEntity;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "customer_appointment_rm",
            joinColumns = @JoinColumn(name = "dsrAccountId"),
            inverseJoinColumns = @JoinColumn(name = "cbCustomerAppointmentId"))
    private Set<DSRAccountEntity> dsrAccountEntities= new HashSet<>();
    private Date createdOn;
//    private Long dsrId;
}
