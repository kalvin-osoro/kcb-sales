package com.ekenya.rnd.backend.fskcb.UserManagement.services;

import com.ekenya.rnd.backend.fskcb.UserManagement.entity.UserRole;
import com.ekenya.rnd.backend.fskcb.UserManagement.entity.UserAccount;
import com.ekenya.rnd.backend.fskcb.UserManagement.repository.RoleRepository;
import com.ekenya.rnd.backend.fskcb.UserManagement.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
public class RoleService {

    private final RoleRepository roleRepository;


    private final UserRepository userRepository;

    public RoleService(RoleRepository roleRepository, UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    public List<UserRole> getRoles(){
        return roleRepository.findAll();
    }

    public void save(UserRole client) {
        roleRepository.save(client);
    }

    public UserRole findById(long id) {
        return roleRepository.findById(id).orElse(null);
    }

    //delete role
    public void delete(long id) {
        roleRepository.delete(findById(id));
    }

    public void assignRole(Long userId, Long roleId){
        UserAccount user = userRepository.findById(userId).orElse(null);
        UserRole role = roleRepository.findById(roleId).orElse(null);
        Set<UserRole> userRoles = (Set<UserRole>) user.getRoles();
        userRoles.add(role);
        user.setRoles(userRoles);
        userRepository.save(user);
    }

    public void unassignRole(Long userId, Long roleId){
        UserAccount user = userRepository.findById(userId).orElse(null);
        Set<UserRole> userRoles = (Set<UserRole>) user.getRoles();
        userRoles.removeIf(x -> Objects.equals(x.getId(), roleId));
        user.setRoles(userRoles);
        userRepository.save(user);
    }

    public Set<UserRole> getUserRoles(UserAccount user){
        return (Set<UserRole>) user.getRoles();
    }


    public List<UserRole> getUserNotRoles(UserAccount user){
        return roleRepository.getUserNotRoles(user.getId());
    }
}
