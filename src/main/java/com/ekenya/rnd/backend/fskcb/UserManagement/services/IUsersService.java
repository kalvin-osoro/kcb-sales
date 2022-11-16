package com.ekenya.rnd.backend.fskcb.UserManagement.services;

import com.ekenya.rnd.backend.fskcb.UserManagement.datasource.entities.UserAccount;

import java.util.List;

public interface IUsersService {

    boolean updateResetPasswordToken(String token, String email);

    UserAccount getByResetPasswordToken(String token);

    boolean updatePassword(UserAccount userAccount, String newPassword);


    UserAccount findById(Long id);


    List<UserAccount> loadAllUsers();
}
