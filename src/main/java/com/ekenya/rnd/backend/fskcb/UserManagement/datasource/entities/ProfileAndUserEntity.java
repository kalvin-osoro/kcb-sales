package com.ekenya.rnd.backend.fskcb.UserManagement.datasource.entities;

import com.ekenya.rnd.backend.utils.Status;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "dbo_users_profiles")
public class ProfileAndUserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="user_id")
    private Long userId;
    @Column(name="profile_id")
    private Long profileId;

    @Column(name="status")
    @Enumerated(EnumType.STRING)
    private Status status= Status.ACTIVE;
}
