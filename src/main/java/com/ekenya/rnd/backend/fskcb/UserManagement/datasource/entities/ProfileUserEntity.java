package com.ekenya.rnd.backend.fskcb.UserManagement.datasource.entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "dbo_users_profiles")
public class ProfileUserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="user_id")
    private Long userId;
    @Column(name="profile_id")
    private Long profileId;
}
