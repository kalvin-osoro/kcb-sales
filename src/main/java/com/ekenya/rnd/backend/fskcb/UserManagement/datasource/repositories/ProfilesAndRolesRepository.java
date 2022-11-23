package com.ekenya.rnd.backend.fskcb.UserManagement.datasource.repositories;

import com.ekenya.rnd.backend.fskcb.UserManagement.datasource.entities.ProfileAndRoleEntity;
import com.ekenya.rnd.backend.fskcb.UserManagement.datasource.entities.UserRoleEntity;
import com.ekenya.rnd.backend.utils.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProfilesAndRolesRepository  extends JpaRepository<ProfileAndRoleEntity, Long> {

    @Query(
            value =  "SELECT FROM dbo_roles_profiles WHERE id NOT IN (SELECT role_id FROM dbo_roles WHERE user_id = ?1)",
            nativeQuery = true
    )
    List<UserRoleEntity> getUsersInRole(Long roleId);
    List<ProfileAndRoleEntity> findAllByProfileId(Long profileId);

    List<ProfileAndRoleEntity> findAllByRoleId(Long id);

    List<ProfileAndRoleEntity> findAllByProfileIdAndStatus(Long profileId, Status status);

    List<ProfileAndRoleEntity> findAllByProfileIdAndRoleId(Long profileId, Long roleId);
    List<ProfileAndRoleEntity> findAllByProfileIdAndRoleIdAndStatus(Long profileId, Long roleId, Status status);
}
