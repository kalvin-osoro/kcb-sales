package com.ekenya.rnd.backend.fskcb.Calender.datasource.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;
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
    private String reason;
    private String dateOfEvent;
    private String period;
    private String phoneNumber;
    private String ownerEmail;
    private String salesCode;
    private String profileCode;
    @ManyToMany
    @JoinTable(
            name = "dbo_owner-member",
            joinColumns = @JoinColumn(name = "appointmentIdd"),
            inverseJoinColumns = @JoinColumn(name = "memberId"))
    List<MembersEntity> members;
    private Date createdOn;
}
