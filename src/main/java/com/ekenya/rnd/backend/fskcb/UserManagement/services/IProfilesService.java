package com.ekenya.rnd.backend.fskcb.UserManagement.services;

import com.ekenya.rnd.backend.fskcb.UserManagement.models.reps.*;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public interface IProfilesService {

    ArrayNode getAllProfiles();

    boolean assignRolesToProfile(AssignProfileRolesRequest model);

    boolean removeRolesFromProfile(RemoveProfileRolesRequest model);

    boolean attemptAddProfile(AddUserProfileRequest model);

    boolean attemptUpdateProfile(UpdateProfileRequest model);

    ArrayNode getRoleProfiles(long roleId);

    ArrayNode getRoleNotProfile(Long roleId);

    boolean drop(long id);

    ObjectNode getProfileDetails(long profileId);

    ArrayNode loadUsersInProfile(long profileId);


    ArrayNode loadRolesInProfile(long profileId);
}
