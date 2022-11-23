package com.ekenya.rnd.backend.fskcb.UserManagement.security;

import com.ekenya.rnd.backend.fskcb.UserManagement.datasource.entities.UserProfileEntity;
import com.ekenya.rnd.backend.fskcb.UserManagement.datasource.entities.UserRoleEntity;
import com.ekenya.rnd.backend.fskcb.UserManagement.datasource.entities.UserAccountEntity;
import com.ekenya.rnd.backend.fskcb.UserManagement.datasource.repositories.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository; //repository for user

    public CustomUserDetailsService(UserRepository userRepository) {

        this.userRepository = userRepository;
    }

    //implementation of UserDetailsService
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String staffNo) throws UsernameNotFoundException {
        UserAccountEntity userAccount = userRepository.findByStaffNo(staffNo).orElseThrow(() ->
                new UsernameNotFoundException("User not found with email : " + staffNo));
        return new User(userAccount.getStaffNo(), userAccount.getPassword(), getAuthorities(userAccount.getRoles()));
    }


//    // map roles to authorities
//    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Set<Role> roles) {
//        return roles.stream()
//                .map(role -> new SimpleGrantedAuthority(role.getName()))
//                .collect(Collectors.toList());
//    }

    private final Collection<? extends GrantedAuthority> getAuthorities(final Collection<UserRoleEntity> roles) {
        final List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        for (UserRoleEntity userRole : roles) {
            authorities.add(new SimpleGrantedAuthority(userRole.getName()));
            for (UserProfileEntity userProfile : userRole.getUserProfiles()) {
                authorities.add(new SimpleGrantedAuthority(userProfile.getName()));
            }
        }
        return authorities;
    }


}