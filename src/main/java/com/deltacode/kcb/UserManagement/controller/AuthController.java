package com.deltacode.kcb.UserManagement.controller;

import com.deltacode.kcb.UserManagement.entity.Role;
import com.deltacode.kcb.UserManagement.entity.UserApp;
import com.deltacode.kcb.UserManagement.payload.JWTAuthResponse;
import com.deltacode.kcb.UserManagement.payload.LoginDto;
import com.deltacode.kcb.UserManagement.payload.SignUpDto;
import com.deltacode.kcb.UserManagement.repository.PrivilegeRepository;
import com.deltacode.kcb.UserManagement.repository.RoleRepository;
import com.deltacode.kcb.UserManagement.repository.UserRepository;
import com.deltacode.kcb.UserManagement.security.JwtTokenProvider;
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
@RequestMapping("/api/auth")

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
    public ResponseEntity<?> authenticateUser(@RequestBody LoginDto loginDto){
        Authentication authentication =authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsernameOrEmail(),
                loginDto.getPassword()));
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
    public ResponseEntity<?> registerUser(@RequestBody SignUpDto signUpDto){
        //check if user already exists by username or email
        if(userRepository.existsByUsername(signUpDto.getUsername())){
            return new ResponseEntity<>("Username already exists", HttpStatus.BAD_REQUEST);
        }
        if(userRepository.existsByEmail(signUpDto.getEmail())){
            return new ResponseEntity<>("Email already exists",HttpStatus.BAD_REQUEST);
        }
        //create new user object
        UserApp userApp = new UserApp();
        userApp.setUsername(signUpDto.getUsername());
        userApp.setDateOfBirth(signUpDto.getDateOfBirth());
        userApp.setEmail(signUpDto.getEmail());
        userApp.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
        userApp.setPhoneNumber(signUpDto.getPhoneNumber());
        userApp.setFirstName(signUpDto.getFirstName());
        userApp.setLastName(signUpDto.getLastName());
        userApp.setMiddleName(signUpDto.getMiddleName());

        userApp.setStaffId(signUpDto.getStaffId());
        userApp.setPhoneNumber(signUpDto.getPhoneNumber());

        Role userRole = roleRepository.findByName("ROLE_ADMIN").get();//get role from db
        userApp.setRoles(Collections.singleton(userRole));//set role to user
        userRepository.save(userApp);//save user to db
        return ResponseEntity.ok("User registered successfully");

    }


}