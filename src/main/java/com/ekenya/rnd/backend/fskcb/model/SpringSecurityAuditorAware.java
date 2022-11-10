package com.ekenya.rnd.backend.fskcb.model;

import com.ekenya.rnd.backend.fskcb.UserManagement.security.CustomUserDetailsService;
import com.ekenya.rnd.backend.fskcb.UserManagement.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public class SpringSecurityAuditorAware implements AuditorAware<String> {
    @Autowired
     private CustomUserDetailsService customUserDetailsService;
    @Autowired
    JwtTokenProvider tokenProvider;

    @Override
    public Optional<String> getCurrentAuditor() {
        // Get the currently logged-in user
        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();
        if(authentication == null || !authentication.isAuthenticated()) {
            return Optional.empty();
        }
        // Get the currently logged-in user
        String username = authentication.getName();
        // load user associated with token
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
        return Optional.of(userDetails.getUsername());


    }

    }


