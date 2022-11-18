package com.ekenya.rnd.backend.fskcb.AdminModule.datasource.repositories;

import com.ekenya.rnd.backend.fskcb.AdminModule.datasource.entities.UserAuditTrailEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IUserAuditTrailRepo extends JpaRepository<UserAuditTrailEntity,Long> {

    List<UserAuditTrailEntity> findByUserId(long userId);
}
