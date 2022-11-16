package com.ekenya.rnd.backend.fskcb.UserManagement.services;

import com.ekenya.rnd.backend.fskcb.UserManagement.datasource.entities.UserRole;
import com.ekenya.rnd.backend.fskcb.UserManagement.datasource.entities.UserAccount;
import com.ekenya.rnd.backend.fskcb.UserManagement.datasource.repositories.RoleRepository;
import com.ekenya.rnd.backend.fskcb.UserManagement.datasource.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
public class RoleService implements IRolesService {

    private final RoleRepository roleRepository;


    private final UserRepository userRepository;

    public RoleService(RoleRepository roleRepository, UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    public List<UserRole> getRoles(){
        return roleRepository.findAll();
    }

    public boolean add(UserRole client) {
        roleRepository.save(client);

        return true;
    }

    public UserRole findById(long id) {
        return roleRepository.findById(id).orElse(null);
    }

    //delete role
    public boolean drop(long id) {
        roleRepository.delete(findById(id));

        return true;
    }

    public boolean assignRole(Long userId, Long roleId){
        UserAccount user = userRepository.findById(userId).orElse(null);
        UserRole role = roleRepository.findById(roleId).orElse(null);
        Set<UserRole> userRoles = (Set<UserRole>) user.getRoles();
        userRoles.add(role);
        user.setRoles(userRoles);
        userRepository.save(user);

        //
        return true;
    }

    public boolean unassignRole(Long userId, Long roleId){
        UserAccount user = userRepository.findById(userId).orElse(null);
        Set<UserRole> userRoles = (Set<UserRole>) user.getRoles();
        userRoles.removeIf(x -> Objects.equals(x.getId(), roleId));
        user.setRoles(userRoles);
        userRepository.save(user);
        //

        return true;
    }

    public Set<UserRole> getUserRoles(UserAccount user){
        return (Set<UserRole>) user.getRoles();
    }


    public List<UserRole> getUserNotRoles(UserAccount user){
        return roleRepository.getUserNotRoles(user.getId());
    }
}
