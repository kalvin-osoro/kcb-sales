package com.deltacode.kcb.UserManagement.service;

import com.deltacode.kcb.UserManagement.entity.UserApp;
import com.deltacode.kcb.UserManagement.repository.UserRepository;
import com.deltacode.kcb.exception.ResourceNotFoundException;
import com.deltacode.kcb.exception.UserNotFoundException;
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
        UserApp userApp = userRepository.findByEmaili(email);
        if (userApp != null) {
            userApp.setResetPasswordToken(token);
            userRepository.save(userApp);
        } else {
            throw new UserNotFoundException("User not found with email " + email);
        }
    }

    public UserApp getByResetPasswordToken(String token) {
        return userRepository.findByResetPasswordToken(token);
    }

    public void updatePassword(UserApp userApp, String newPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(newPassword);
        userApp.setPassword(encodedPassword);

        userApp.setResetPasswordToken(null);
        userRepository.save(userApp);
    }
    public UserApp findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("UseApp","id", + id));
    }




}
