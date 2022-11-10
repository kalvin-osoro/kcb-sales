package com.ekenya.rnd.backend.fskcb.UserManagement.repository;

import com.ekenya.rnd.backend.fskcb.UserManagement.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    @Query(
            value =  "SELECT FROM roles_tb WHERE id NOT IN (SELECT role_id FROM user_roles WHERE user_id = ?1)",
            nativeQuery = true
    )
    List<Role> getUserNotRoles(Long userId);
    Optional<Role> findByName(String name);


}


