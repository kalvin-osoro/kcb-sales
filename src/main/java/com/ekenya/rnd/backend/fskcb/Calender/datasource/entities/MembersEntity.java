package com.ekenya.rnd.backend.fskcb.Calender.datasource.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "dbo_calendar_members")
@DynamicInsert
@DynamicUpdate
public class MembersEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
    private String name;
    private String salesCode;
    private String phoneNumber;
    private String email;
    @ManyToMany
    Set<MeetingDetailsEntity> meetingDetailsEntities;

}
