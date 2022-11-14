package com.ekenya.rnd.backend.fskcb.UserManagement.services;

import com.ekenya.rnd.backend.fskcb.UserManagement.datasource.entities.Privilege;
import com.ekenya.rnd.backend.fskcb.UserManagement.datasource.entities.UserRole;
import com.ekenya.rnd.backend.fskcb.UserManagement.datasource.repositories.PrivilegeRepository;
import com.ekenya.rnd.backend.fskcb.UserManagement.datasource.repositories.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
@Service

public class PrivilegeService {
    public final PrivilegeRepository privilegeRepository;
    
    public final RoleRepository roleRepository;

    public PrivilegeService(PrivilegeRepository privilegeRepository, RoleRepository roleRepository) {
        this.privilegeRepository = privilegeRepository;
        this.roleRepository = roleRepository;
    }
    public List<Privilege> getPrivilege(){
        return privilegeRepository.findAll();
    }

    public void assignPrivilege(Long roleId, Long privilegeId){
        UserRole userRole = roleRepository.findById(roleId).orElse(null);
        Privilege privilege = privilegeRepository.findById(privilegeId).orElse(null);
        Set<Privilege> rolePrivileges = (Set<Privilege>) userRole.getPrivileges();
        rolePrivileges.add(privilege);
        userRole.setPrivileges(rolePrivileges);
        roleRepository.save(userRole);
    }
    public void unassignPrivilege(Long roleId, Long privilegeId){
        UserRole userRole = roleRepository.findById(roleId).orElse(null);
        Set<Privilege> rolePrivileges = (Set<Privilege>) userRole.getPrivileges();
        rolePrivileges.removeIf(x -> x.getId().equals(privilegeId));
        userRole.setPrivileges(rolePrivileges);
        roleRepository.save(userRole);
    }

    public void save(Privilege privilege) {
        privilegeRepository.save(privilege);
    }
    public Set<Privilege> getRolePrivileges(UserRole userRole){
        return (Set<Privilege>) userRole.getPrivileges();
    }
   public List<Privilege> getRoleNotPrivilege(Long roleId){
        return privilegeRepository.getRoleNotPrivilege(roleId);
    }
    public void delete(long id) {
        privilegeRepository.delete(findById(id));
    }

    public Privilege findById(long id) {
        return privilegeRepository.findById(id).orElse(null);
    }
}
