package com.ekenya.rnd.backend.fskcb.DSRModule.models;

import com.ekenya.rnd.backend.fskcb.utils.Status;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(	name = "dsr_details")
@DynamicInsert
@DynamicUpdate
public class DSRDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="email")
    private String email;

    @Column(name="username")
    private String username;

    @Column(name="mobile_no")
    private String mobileNo;

    @Column(name="staff_no")
    private String staffNo;

    @Column(name="status")
    @Enumerated(EnumType.STRING)
    private Status status= Status.ACTIVE;

    @Column(name="first_name")
    private String firstName;

    @Column(name="last_name")
    private String lastName;

    @Column(name="other_name")
    private String otherName;

    @Column(name="location")
    private String location;

    @Column(name="gender")
    private String gender;

    @Column(name="national_id")
    private String idNumber;

    @Column(name="system_user_id")
    private Long systemUserId;

    @ManyToOne
    private DSRTeam dsrTeam;

    @Column(name="created_by")
    private String createdBy;

    @Column(name="created_on")
    private Date createdOn;

    @Column(name="updated_on")
    private Date updatedOn;

    @Column(name="updated_by")
    private String updatedBy;

}
