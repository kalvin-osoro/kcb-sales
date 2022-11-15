package com.ekenya.rnd.backend.fskcb.AuthModule.services;

import com.ekenya.rnd.backend.fskcb.AuthModule.models.reqs.*;
import com.ekenya.rnd.backend.fskcb.AuthModule.models.resp.LoginResponse;
import com.ekenya.rnd.backend.fskcb.UserManagement.datasource.repositories.UserProfilesRepository;
import com.ekenya.rnd.backend.fskcb.UserManagement.datasource.repositories.RoleRepository;
import com.ekenya.rnd.backend.fskcb.UserManagement.datasource.repositories.UserRepository;
import com.ekenya.rnd.backend.fskcb.UserManagement.security.JwtTokenProvider;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AuthService implements IAuthService{
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired

    private UserProfilesRepository userProfilesRepository;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Override
    public LoginResponse attemptLogin(LoginRequest model) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(model.getStaffNo(),
                model.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        //get token from token provider
        String token= tokenProvider.generateToken(authentication);
        //get user details from authentication
        UserDetails userDetails=(UserDetails)authentication.getPrincipal();
        String username=userDetails.getUsername();
        //get user roles
        List<String> roles=userDetails.getAuthorities().stream().map(item->item.getAuthority()).collect(Collectors.toList());


        //Response
        LoginResponse response = new LoginResponse();
        response.setToken(token);
        response.setExpires(Calendar.getInstance().getTime());
        response.setProfiles(roles);
        response.setType("Bearer");
        //
        return response;
    }

    @Override
    public boolean attemptChangePIN(ChangePINRequest model) {
        return false;
    }

    @Override
    public int accountExists(LookupRequest model) {

        try {
            return 2;
        }catch (Exception ex){

        }

        return -1;
    }

    @Override
    public boolean sendVerificationCode(SendVerificationCodeRequest model) {
        return false;
    }

    @Override
    public boolean validateVerificationCode(ValidateVerificationCodeRequest model) {
        return false;
    }

    @Override
    public List<ObjectNode> loadSecurityQuestions() {
        return null;
    }

    @Override
    public boolean setSecurityQuestions(SetSecurityQnsRequest model) {
        return false;
    }

    @Override
    public boolean validateSecurityQuestions(ValidateSecurityQnsRequest model) {
        return false;
    }

    @Override
    public boolean attemptCreatePIN(CreatePINRequest model) {
        return false;
    }

    @Override
    public boolean attemptChangePassword(ChangePasswordRequest model) {
        return false;
    }

    @Override
    public boolean resetDSRPIN(ResetDSRPINRequest model) {
        return false;
    }
}
