package com.ekenya.rnd.backend.fskcb.UserManagement.services;

import com.ekenya.rnd.backend.fskcb.UserManagement.datasource.entities.UserAccount;
import com.ekenya.rnd.backend.fskcb.UserManagement.datasource.repositories.UserRepository;
import com.ekenya.rnd.backend.fskcb.exception.ResourceNotFoundException;
import com.ekenya.rnd.backend.fskcb.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UsersService implements IUsersService {
    @Autowired
    private UserRepository userRepository;


    public boolean updateResetPasswordToken(String token, String email) throws UserNotFoundException {
        UserAccount userAccount = userRepository.findByEmail(email).get();
        if (userAccount != null) {
            userAccount.setResetPasswordToken(token);
            userRepository.save(userAccount);

            return true;
        } else {
            throw new UserNotFoundException("User not found with email " + email);
        }

    }

    public UserAccount getByResetPasswordToken(String token) {
        return userRepository.findByResetPasswordToken(token);
    }

    public boolean updatePassword(UserAccount userAccount, String newPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(newPassword);
        userAccount.setPassword(encodedPassword);

        userAccount.setResetPasswordToken(null);
        userRepository.save(userAccount);

        return true;
    }
    public UserAccount findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("UseApp","id", + id));
    }

    @Override
    public List<UserAccount> loadAllUsers() {
        return userRepository.findAll();
    }


}
