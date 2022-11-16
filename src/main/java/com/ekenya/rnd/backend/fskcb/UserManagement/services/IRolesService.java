package com.ekenya.rnd.backend.fskcb.UserManagement.services;

import com.ekenya.rnd.backend.fskcb.UserManagement.datasource.entities.UserAccount;
import com.ekenya.rnd.backend.fskcb.UserManagement.datasource.entities.UserRole;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Set;

public interface IRolesService {
    List<UserRole> getRoles();
    boolean add(UserRole client);

    UserRole findById(long id);

    boolean drop(long id);

    boolean assignRole(Long userId, Long roleId);

    boolean unassignRole(Long userId, Long roleId);

    Set<UserRole> getUserRoles(UserAccount user);
}
