package com.ekenya.rnd.backend.fskcb.DSRModule.datasource.entities;

import com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.entities.AcquiringTargetEntity;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.datasource.entities.DFSVoomaTargetEntity;
import com.ekenya.rnd.backend.utils.Status;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@DynamicInsert
@DynamicUpdate
@Table(name = "dbo_dsr_accounts")
public class DSRAccountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="phone_no")
    private String phoneNo;

    @Column(name="phone_verified")
    private boolean phoneNoVerified;

    @Column(name="email")
    private String email;

//    @Column(name="username")
//    private String username;

    @Column(name="staff_no")
    private String staffNo;

    @Column(name="sales_code")
    private String salesCode;

    @Column(name="status")
    @Enumerated(EnumType.STRING)
    private Status status= Status.ACTIVE;

    @Column(name="full_name")
    private String fullName;

    @Column(name="location")
    private String location;

    @Column(name="gender")
    private String gender;

    @Column(name="national_id")
    private String idNumber;
    //many to one with acquiring target
    @ManyToOne
    @JoinColumn(name="acquiring_target_id")
    private AcquiringTargetEntity acquiringTargetEntity;

    @Column(name="team_id")
    private Long teamId;

    @Column(name="created_by")
    private String createdBy;

    @Column(name="created_on")
    private Date createdOn = Calendar.getInstance().getTime();

    @Column(name="updated_on")
    private Date updatedOn;

    @Column(name="updated_by")
    private String updatedBy;

    @Column(name="expires_on")
    private Date expiryDate;

    @Column(name="locked")
    private Boolean locked;

    @Column(name="date_unlocked")
    private Date dateLocked;
    @Column(name="date_locked")
    private Date dateUnlocked;
}
