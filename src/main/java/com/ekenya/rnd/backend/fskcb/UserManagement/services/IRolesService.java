package com.ekenya.rnd.backend.fskcb.UserManagement.services;

import com.ekenya.rnd.backend.fskcb.UserManagement.models.reps.AddUserRoleRequest;
import com.ekenya.rnd.backend.fskcb.UserManagement.models.reps.AssignRoleToUserRequest;
import com.ekenya.rnd.backend.fskcb.UserManagement.models.reps.RemoveUserFromRole;
import com.ekenya.rnd.backend.fskcb.UserManagement.models.reps.UpdateUserRoleRequest;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public interface IRolesService {
    ArrayNode loadAllRoles();
    boolean attemptCreateUserRole(AddUserRoleRequest model);

    boolean updateUserRole(UpdateUserRoleRequest model);
    ObjectNode getUserRoleDetails(long id);

    boolean deleteUserRole(long id);

    boolean assignRole(AssignRoleToUserRequest model);

    boolean unassignRole(RemoveUserFromRole model);

    ArrayNode loadUserRoles(Long userId);

    ArrayNode getUserNotRoles(Long userId);

}
