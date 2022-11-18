package com.ekenya.rnd.backend.fskcb.AuthModule.datasource.repositories;

import com.ekenya.rnd.backend.fskcb.AuthModule.datasource.entities.AuthCodeType;
import com.ekenya.rnd.backend.fskcb.AuthModule.datasource.entities.SecurityAuthCodeEntity;
import com.ekenya.rnd.backend.fskcb.UserManagement.datasource.entities.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ISecurityAuthCodesRepository extends JpaRepository<SecurityAuthCodeEntity, Long> {

    List<SecurityAuthCodeEntity> findAllByUserId(Long id);

    Optional<SecurityAuthCodeEntity> findAllByCode(String code);
    List<SecurityAuthCodeEntity> findAllByExpiredFalse();
}
