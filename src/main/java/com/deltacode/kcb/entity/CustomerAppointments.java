package com.deltacode.kcb.entity;

import com.deltacode.kcb.utils.Status;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

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
    @Column(name="appointment_date")
    private String appointmentDate;
    @Column(name="appointment_duration")
    private String appointmentDuration;
    @Enumerated(EnumType.STRING)
    private Status status= Status.ACTIVE;
    @Column(name="account_name")
    private String accountName;
    @Column(name="customer_type")
    private String customerType;
    @Column(name="phone_number")
    private String phoneNumber;
    @Lob
    @Column(name="reson_for_appointment", length=512)
    private String resonsForAppointment;
    @Column(name="created_on")
    private Date createdOn;

    @Column(name="updated_on")
    private Date updatedOn;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "updated_by")
    private Long updatedBy;
}
