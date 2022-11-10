package com.ekenya.rnd.backend.fskcb.UserManagement.controller;

import com.ekenya.rnd.backend.fskcb.UserManagement.entity.Role;
import com.ekenya.rnd.backend.fskcb.UserManagement.entity.UserAccount;
import com.ekenya.rnd.backend.fskcb.UserManagement.payload.JWTAuthResponse;
import com.ekenya.rnd.backend.fskcb.UserManagement.payload.LoginRequest;
import com.ekenya.rnd.backend.fskcb.UserManagement.payload.AddUserRequest;
import com.ekenya.rnd.backend.fskcb.UserManagement.repository.PrivilegeRepository;
import com.ekenya.rnd.backend.fskcb.UserManagement.repository.RoleRepository;
import com.ekenya.rnd.backend.fskcb.UserManagement.repository.UserRepository;
import com.ekenya.rnd.backend.fskcb.UserManagement.security.JwtTokenProvider;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@Api(value = "Auth Controller for Field Agent Rest Api")
@RestController
@RequestMapping

public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired

    private PrivilegeRepository privilegeRepository;

    @Autowired
    private JwtTokenProvider tokenProvider;

    public AuthController() {
    }

    @PostMapping("/login")
    @ApiOperation(value = "Login Api")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest){
        Authentication authentication =authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getStaffNo(),
                loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        //get token from token provider
        String token= tokenProvider.generateToken(authentication);
        //get user details from authentication
        UserDetails userDetails=(UserDetails)authentication.getPrincipal();
        String username=userDetails.getUsername();
        //get user roles
        List<String> roles=userDetails.getAuthorities().stream().map(item->item.getAuthority()).collect(Collectors.toList());



        return ResponseEntity.ok(new JWTAuthResponse(token,username,roles));
        //return response entity

//        return ResponseEntity.ok(new JWTAuthResponse(token, "Bearer"));

    }
    @PostMapping("/signup")
    @ApiOperation(value = "Signup Api")
    public ResponseEntity<?> registerUser(@RequestBody AddUserRequest addUserRequest){
        //check if user already exists by username or email
        if(userRepository.existsByUsername(addUserRequest.getStaffNo())){
            return new ResponseEntity<>("Username already exists", HttpStatus.BAD_REQUEST);
        }
        if(userRepository.existsByEmail(addUserRequest.getEmail())){
            return new ResponseEntity<>("Email already exists",HttpStatus.BAD_REQUEST);
        }
        //create new user object
        UserAccount userAccount = new UserAccount();
        userAccount.setUsername(addUserRequest.getStaffNo());
        //userAccount.setDateOfBirth(addUserVM.getDateOfBirth());
        userAccount.setEmail(addUserRequest.getEmail());
        userAccount.setPassword(passwordEncoder.encode(addUserRequest.getPassword()));
        //userAccount.setPhoneNumber(addUserVM.getPhoneNumber());
        //userAccount.setFirstName(addUserVM.getFirstName());
        //userAccount.setLastName(addUserVM.getLastName());
        //userAccount.setMiddleName(addUserVM.getMiddleName());

        //userAccount.setStaffId(addUserVM.getStaffId());
        //userAccount.setPhoneNumber(addUserVM.getPhoneNumber());

        Role userRole = roleRepository.findByName("ROLE_ADMIN").get();//get role from db
        userAccount.setRoles(Collections.singleton(userRole));//set role to user
        userRepository.save(userAccount);//save user to db
        return ResponseEntity.ok("User registered successfully");

    }


}