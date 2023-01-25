package com.ekenya.rnd.backend.fskcb.CorporateBankingModule.datasource.entities;

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
@Table(name = "rm")
@DynamicInsert
@DynamicUpdate
public class CBCustomerAppointmentRM {
    @Id
    @GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
    private Long id;
    private Long appointmentId;
    private Long rmId;
    private String rmName;
    private String rmPhoneNumber;


   @ManyToMany(mappedBy = "rm")
    private Set<CBCustomerAppointment> appointment = new HashSet<>();



    private Date createdOn;

}
