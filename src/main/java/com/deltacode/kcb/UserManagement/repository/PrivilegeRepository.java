package com.deltacode.kcb.UserManagement.repository;


import com.deltacode.kcb.UserManagement.entity.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PrivilegeRepository extends JpaRepository<Privilege,Long> {
    @Query(
            value =  "SELECT FROM privileges_tb WHERE id NOT IN (SELECT privilege_id FROM role_privileges WHERE role_id = ?1)",
            nativeQuery = true
    )
    List<Privilege> getRoleNotPrivilege(Long roleId);
    Optional<Privilege> findByName(String name);
}
