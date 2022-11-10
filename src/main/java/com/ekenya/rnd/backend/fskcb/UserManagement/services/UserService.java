package com.ekenya.rnd.backend.fskcb.UserManagement.services;

import com.ekenya.rnd.backend.fskcb.UserManagement.entity.UserAccount;
import com.ekenya.rnd.backend.fskcb.UserManagement.repository.UserRepository;
import com.ekenya.rnd.backend.fskcb.exception.ResourceNotFoundException;
import com.ekenya.rnd.backend.fskcb.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
@Service
@Transactional
public class UserService {
    @Autowired
    private UserRepository userRepository;


    public void updateResetPasswordToken(String token, String email) throws UserNotFoundException {
        UserAccount userAccount = userRepository.findByEmaili(email);
        if (userAccount != null) {
            userAccount.setResetPasswordToken(token);
            userRepository.save(userAccount);
        } else {
            throw new UserNotFoundException("User not found with email " + email);
        }
    }

    public UserAccount getByResetPasswordToken(String token) {
        return userRepository.findByResetPasswordToken(token);
    }

    public void updatePassword(UserAccount userAccount, String newPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(newPassword);
        userAccount.setPassword(encodedPassword);

        userAccount.setResetPasswordToken(null);
        userRepository.save(userAccount);
    }
    public UserAccount findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("UseApp","id", + id));
    }




}
