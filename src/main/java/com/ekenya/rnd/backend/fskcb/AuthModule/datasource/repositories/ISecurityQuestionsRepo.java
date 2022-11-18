package com.ekenya.rnd.backend.fskcb.AuthModule.datasource.repositories;

import com.ekenya.rnd.backend.fskcb.AuthModule.datasource.entities.SecurityQuestionEntity;
import com.ekenya.rnd.backend.utils.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ISecurityQuestionsRepo extends JpaRepository<SecurityQuestionEntity, Long> {

    Optional<SecurityQuestionEntity> findByIdAndStatus(Long id, Status status);
}
