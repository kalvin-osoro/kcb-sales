package com.ekenya.rnd.backend.fskcb.UserManagement.services;

import com.ekenya.rnd.backend.fskcb.UserManagement.datasource.entities.AccountType;
import com.ekenya.rnd.backend.fskcb.UserManagement.datasource.entities.UserAccountEntity;
import com.ekenya.rnd.backend.fskcb.UserManagement.models.DeleteWrapper;
import com.ekenya.rnd.backend.fskcb.UserManagement.models.reps.*;
import com.ekenya.rnd.backend.fskcb.UserManagement.payload.AddAdminUserRequest;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IUsersService {

    boolean updateResetPasswordToken(String token, String email);

    UserAccountEntity getByResetPasswordToken(String token);

    boolean attemptUpdatePassword(UpdatePasswordRequest model);

    UserAccountEntity findById(Long id);

    UserAccountEntity findByStaffNo(String staffNo);

    boolean attemptCreateUser(AddAdminUserRequest model, AccountType type, boolean verified);
    ObjectNode attemptImportUsers(MultipartFile importFile);

    List<ObjectNode> loadAllUsers();

    boolean syncUsersWithCRM();

    ObjectNode loadUserDetails(AdminUserDetailsRequest model);

    boolean attemptResetPassword(ResetUserPasswordRequest model);
    boolean assignUserToProfiles(AssignUserProfileRequest model);
    boolean updateUserProfiles(UpdateUserProfilesRequest model);

    boolean attemptBlockUser(Long userId);

    boolean attemptUnblockUser(Long userId);

    ArrayNode loadUserAuditTrail(Long userId);

    boolean attemptCreateSecurityQuestion(AddSecurityQnRequest model);

    List<ObjectNode> loadAllSecurityQuestions();

    boolean attemptDeleteUser(DeleteWrapper request);
}
