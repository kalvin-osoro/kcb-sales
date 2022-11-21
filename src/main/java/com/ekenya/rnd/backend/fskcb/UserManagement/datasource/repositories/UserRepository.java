package com.ekenya.rnd.backend.fskcb.UserManagement.datasource.repositories;

import com.ekenya.rnd.backend.fskcb.UserManagement.datasource.entities.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<UserAccount, Long> {
    //retrieve user by email

    Optional<UserAccount> findByStaffNoAndPhoneNumber(String staffNo, String phoneNo);
    Optional<UserAccount> findByEmail(String email);
    Optional<UserAccount> findByStaffNo(String name);
    Boolean existsByStaffNo(String staffNo);
    Boolean existsByEmail(String email);
    UserAccount findByResetPasswordToken(String token);
//    @Query("SELECT c FROM UserAccount c WHERE c.email = ?1")
//    UserAccount findByEmail(String email);
    //Optional<UserAccount> findByStaffNo(String staffNo);



}

