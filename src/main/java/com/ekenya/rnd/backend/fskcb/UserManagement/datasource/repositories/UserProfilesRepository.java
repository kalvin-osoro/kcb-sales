package com.ekenya.rnd.backend.fskcb.UserManagement.datasource.repositories;


import com.ekenya.rnd.backend.fskcb.UserManagement.datasource.entities.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserProfilesRepository extends JpaRepository<UserProfile,Long> {
    @Query(
            value =  "SELECT FROM dbo_user_profiles WHERE id NOT IN (SELECT privilege_id FROM role_privileges WHERE role_id = ?1)",
            nativeQuery = true
    )
    List<UserProfile> getRoleNotPrivilege(Long roleId);
    Optional<UserProfile> findByName(String name);

}
