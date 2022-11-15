package com.ekenya.rnd.backend.fskcb.UserManagement.datasource.repositories;

import com.ekenya.rnd.backend.fskcb.UserManagement.datasource.entities.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface RoleRepository extends JpaRepository<UserRole, Long> {
    @Query(
            value =  "SELECT FROM dbo_roles WHERE id NOT IN (SELECT role_id FROM dbo_user_roles WHERE user_id = ?1)",
            nativeQuery = true
    )
    List<UserRole> getUserNotRoles(Long userId);
    Optional<UserRole> findByName(String name);


}


