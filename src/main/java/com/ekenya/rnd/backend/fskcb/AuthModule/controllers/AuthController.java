package com.ekenya.rnd.backend.fskcb.AuthModule.controllers;

import com.ekenya.rnd.backend.fskcb.AuthModule.models.LookupRequest;
import com.ekenya.rnd.backend.fskcb.UserManagement.entity.Privilege;
import com.ekenya.rnd.backend.fskcb.UserManagement.entity.UserRole;
import com.ekenya.rnd.backend.fskcb.UserManagement.entity.UserAccount;
import com.ekenya.rnd.backend.fskcb.AuthModule.models.LoginRequest;
import com.ekenya.rnd.backend.fskcb.UserManagement.payload.AddUserRequest;
import com.ekenya.rnd.backend.fskcb.UserManagement.repository.PrivilegeRepository;
import com.ekenya.rnd.backend.fskcb.UserManagement.repository.RoleRepository;
import com.ekenya.rnd.backend.fskcb.UserManagement.repository.UserRepository;
import com.ekenya.rnd.backend.fskcb.UserManagement.security.JwtTokenProvider;
import com.ekenya.rnd.backend.responses.AppResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@Api(value = "Auth Controller for Field Agent Rest Api")
@RestController
@RequestMapping(path = "/api/v1")
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
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getStaffNo(),
                loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        //get token from token provider
        String token= tokenProvider.generateToken(authentication);
        //get user details from authentication
        UserDetails userDetails=(UserDetails)authentication.getPrincipal();
        String username=userDetails.getUsername();
        //get user roles
        List<String> roles=userDetails.getAuthorities().stream().map(item->item.getAuthority()).collect(Collectors.toList());


        //Response
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode node = objectMapper.createObjectNode();
        node.put("token",token);
        node.putPOJO("username",username);
        node.putPOJO("roles",roles);
        node.putPOJO("profiles",objectMapper.createArrayNode());
        return ResponseEntity.ok(new AppResponse(1,node,"User login successful"));

    }


    @PostMapping("/lookup-user")
    @ApiOperation(value = "Login Api")
    public ResponseEntity<?> lookupUser(@RequestBody LookupRequest loginRequest) {

        //TOD



        //Response
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode node = objectMapper.createObjectNode();
        //
        return ResponseEntity.ok(new AppResponse(1,node,"User login successful"));
    }

}