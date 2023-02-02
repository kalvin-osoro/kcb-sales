package com.ekenya.rnd.backend.fskcb.Calender.datasource.entities;

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
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "dbo_calendar")
@DynamicInsert
@DynamicUpdate
public class MeetingDetailsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
    private String owner;
    private String venue;
    private String time;
    private String reason;
    private String dateOfEvent;
    private String period;
    private Boolean isOnline=false;
    private String ownerPhone;
    private String customerPhoneNumber;
    private String customerName;
    private String ownerEmail;
    private String salesCode;
    private Long dsrId;
    private String profileCode;
    @ManyToMany
    @JoinTable(name = "appointment_member",
            joinColumns = @JoinColumn(name = "appointmentId"),
            inverseJoinColumns = @JoinColumn(name = "memberId")
    )
    private Set<DSRAccountEntity> assignedMembers = new HashSet<>();
    private Date createdOn;
}
