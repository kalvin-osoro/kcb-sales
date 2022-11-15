package com.ekenya.rnd.backend.fskcb.UserManagement.datasource.entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "dbo_roles_profiles")
public class ProfileRoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="role_id")
    private Long roleId;
    @Column(name="profile_id")
    private Long profileId;
}
