package com.ekenya.rnd.backend.fskcb.UserManagement.datasource.repositories;

import com.ekenya.rnd.backend.fskcb.UserManagement.datasource.entities.AccountType;
import com.ekenya.rnd.backend.fskcb.UserManagement.datasource.entities.UserAccountEntity;
import com.ekenya.rnd.backend.utils.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<UserAccountEntity, Long> {
    //
    List<UserAccountEntity> findAllByAccountTypeAndStatus(AccountType type, Status status);
    Optional<UserAccountEntity> findByStaffNoAndPhoneNumber(String staffNo, String phoneNo);
    //retrieve user by email
    Optional<UserAccountEntity> findByEmail(String email);
    Optional<UserAccountEntity> findByStaffNo(String name);
    Boolean existsByStaffNo(String staffNo);
    Boolean existsByEmail(String email);
    UserAccountEntity findByResetPasswordToken(String token);
//    @Query("SELECT c FROM UserAccount c WHERE c.email = ?1")
//    UserAccount findByEmail(String email);
    //Optional<UserAccount> findByStaffNo(String staffNo);



}

