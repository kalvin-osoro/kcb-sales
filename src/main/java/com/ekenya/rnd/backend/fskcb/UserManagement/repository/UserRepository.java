package com.ekenya.rnd.backend.fskcb.UserManagement.repository;

import com.ekenya.rnd.backend.fskcb.UserManagement.entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<UserAccount, Long> {
    //retrieve user by email

    Optional<UserAccount> findByUsernameOrEmail(String username, String email);
    Optional<UserAccount> findByUsername(String username);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    UserAccount findByResetPasswordToken(String token);
        @Query("SELECT c FROM UserApp c WHERE c.email = ?1")
        UserAccount findByEmaili(String email);
        Optional<UserAccount> findByPhoneNumberAndStaffId(String phoneNo, String staffId);



}

