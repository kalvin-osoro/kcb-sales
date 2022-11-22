package com.ekenya.rnd.backend.fskcb.UserManagement.services;

import com.ekenya.rnd.backend.fskcb.UserManagement.datasource.entities.UserAccount;
import com.ekenya.rnd.backend.fskcb.UserManagement.models.reps.*;
import com.ekenya.rnd.backend.fskcb.UserManagement.payload.AddUserRequest;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IUsersService {

    boolean updateResetPasswordToken(String token, String email);

    UserAccount getByResetPasswordToken(String token);

    boolean attemptUpdatePassword(UpdatePasswordRequest model);

    UserAccount findById(Long id);

    UserAccount findByStaffNo(String staffNo);

    boolean attemptCreateUser(AddUserRequest model);
    ObjectNode attemptImportUsers(MultipartFile importFile);

    List<ObjectNode> loadAllUsers();

    boolean syncUsersWithCRM();

    ObjectNode loadUserDetails(long userId);

    boolean attemptResetPassword(ResetUserPasswordRequest model);
    boolean assignUserToProfiles(AssignUserProfileRequest model);
    boolean updateUserProfiles(UpdateUserProfileRequest model);

    boolean attemptBlockUser(Long userId);

    boolean attemptUnblockUser(Long userId);

    ArrayNode loadUserAuditTrail(Long userId);

    boolean attemptCreateSecurityQuestion(AddSecurityQnRequest model);

    List<ObjectNode> loadAllSecurityQuestions();
}
