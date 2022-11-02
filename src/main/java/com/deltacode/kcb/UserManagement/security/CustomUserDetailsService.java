package com.deltacode.kcb.UserManagement.security;

import com.deltacode.kcb.UserManagement.entity.Privilege;
import com.deltacode.kcb.UserManagement.entity.Role;
import com.deltacode.kcb.UserManagement.entity.UserApp;
import com.deltacode.kcb.UserManagement.repository.UserRepository;
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
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        UserApp userApp = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail).orElseThrow(() ->
                new UsernameNotFoundException("User not found with username or email : " + usernameOrEmail));
        return new User(userApp.getUsername(), userApp.getPassword(), getAuthorities(userApp.getRoles()));
    }


//    // map roles to authorities
//    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Set<Role> roles) {
//        return roles.stream()
//                .map(role -> new SimpleGrantedAuthority(role.getName()))
//                .collect(Collectors.toList());
//    }

    private final Collection<? extends GrantedAuthority> getAuthorities(final Collection<Role> roles) {
        final List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        for (Role role: roles) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
            for (Privilege privilege: role.getPrivileges()) {
                authorities.add(new SimpleGrantedAuthority(privilege.getName()));
            }
        }
        return authorities;
    }


}