package com.ekenya.rnd.backend.fskcb.UserManagement.services;

import com.ekenya.rnd.backend.fskcb.UserManagement.datasource.entities.UserProfile;
import com.ekenya.rnd.backend.fskcb.UserManagement.datasource.entities.UserRole;
import com.ekenya.rnd.backend.fskcb.UserManagement.datasource.repositories.UserProfilesRepository;
import com.ekenya.rnd.backend.fskcb.UserManagement.datasource.repositories.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
@Service

public class PrivilegeService {
    public final UserProfilesRepository userProfilesRepository;
    
    public final RoleRepository roleRepository;

    public PrivilegeService(UserProfilesRepository userProfilesRepository, RoleRepository roleRepository) {
        this.userProfilesRepository = userProfilesRepository;
        this.roleRepository = roleRepository;
    }
    public List<UserProfile> getPrivilege(){
        return userProfilesRepository.findAll();
    }

    public void assignPrivilege(Long roleId, Long privilegeId){
        UserRole userRole = roleRepository.findById(roleId).orElse(null);
        UserProfile userProfile = userProfilesRepository.findById(privilegeId).orElse(null);
        Set<UserProfile> roleUserProfiles = (Set<UserProfile>) userRole.getUserProfiles();
        roleUserProfiles.add(userProfile);
        userRole.setUserProfiles(roleUserProfiles);
        roleRepository.save(userRole);
    }
    public void unassignPrivilege(Long roleId, Long privilegeId){
        UserRole userRole = roleRepository.findById(roleId).orElse(null);
        Set<UserProfile> roleUserProfiles = (Set<UserProfile>) userRole.getUserProfiles();
        roleUserProfiles.removeIf(x -> x.getId().equals(privilegeId));
        userRole.setUserProfiles(roleUserProfiles);
        roleRepository.save(userRole);
    }

    public void save(UserProfile userProfile) {
        userProfilesRepository.save(userProfile);
    }
    public Set<UserProfile> getRolePrivileges(UserRole userRole){
        return (Set<UserProfile>) userRole.getUserProfiles();
    }
   public List<UserProfile> getRoleNotPrivilege(Long roleId){
        return userProfilesRepository.getRoleNotPrivilege(roleId);
    }
    public void delete(long id) {
        userProfilesRepository.delete(findById(id));
    }

    public UserProfile findById(long id) {
        return userProfilesRepository.findById(id).orElse(null);
    }
}
