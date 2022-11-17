package com.ekenya.rnd.backend.fskcb.AuthModule.datasource.repositories;

import com.ekenya.rnd.backend.fskcb.AuthModule.datasource.entities.SecQuestionOptionEntity;
import com.ekenya.rnd.backend.fskcb.AuthModule.datasource.entities.SecurityAuthCodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ISecurityQuestionOptionsRepo  extends JpaRepository<SecQuestionOptionEntity, Long> {
}
