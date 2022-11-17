package com.ekenya.rnd.backend.fskcb.UserManagement.services;

import com.ekenya.rnd.backend.fskcb.UserManagement.datasource.entities.UserProfile;
import com.ekenya.rnd.backend.fskcb.UserManagement.datasource.entities.UserRole;

import java.util.List;
import java.util.Set;

public interface IProfilesService {

    List<UserProfile> getAllProfiles();

    boolean assignPrivilege(Long roleId, Long privilegeId);

    boolean unassignPrivilege(Long roleId, Long privilegeId);

    boolean add(UserProfile userProfile);

    Set<UserProfile> getRoleProfiles(UserRole userRole);

    List<UserProfile> getRoleNotProfile(Long roleId);

    boolean drop(long id);

    UserProfile findById(long id);
}
