package com.ekenya.rnd.backend.fskcb.UserManagement.datasource.repositories;

import com.ekenya.rnd.backend.fskcb.UserManagement.datasource.entities.ProfileRoleEntity;
import com.ekenya.rnd.backend.fskcb.UserManagement.datasource.entities.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProfilesAndRolesRepository  extends JpaRepository<ProfileRoleEntity, Long> {

    @Query(
            value =  "SELECT FROM dbo_roles_profiles WHERE id NOT IN (SELECT role_id FROM dbo_roles WHERE user_id = ?1)",
            nativeQuery = true
    )
    List<UserRole> getUsersInRole(Long roleId);
}
