package com.ekenya.rnd.backend.fskcb.entity;

import com.ekenya.rnd.backend.utils.Status;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(	name = "CustomerAppointments")
@DynamicInsert
@DynamicUpdate
public class CustomerAppointments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String appointmentDate;
    private String appointmentDuration;
    @Enumerated(EnumType.STRING)
    private Status status= Status.ACTIVE;
    private String accountName;
    private String customerType;
    private String phoneNumber;
    @Lob
    private String resonsForAppointment;
    private Date createdOn;

}
