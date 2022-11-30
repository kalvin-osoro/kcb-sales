package com.ekenya.rnd.backend.fskcb.AuthModule.datasource.repositories;

import com.ekenya.rnd.backend.fskcb.AuthModule.datasource.entities.SecurityAuthCodeEntity;
import com.ekenya.rnd.backend.fskcb.AuthModule.datasource.entities.SecurityQuestionAnswerEntity;
import com.ekenya.rnd.backend.utils.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ISecurityQuestionAnswersRepo  extends JpaRepository<SecurityQuestionAnswerEntity, Long> {

    Optional<SecurityQuestionAnswerEntity> findAllByQuestionIdAndUserIdAndStatus(Long qnId,long userId, Status status);

    Optional<SecurityQuestionAnswerEntity> findAllByIdAndUserIdAndStatus(Long id,long uid, Status status);

    List<SecurityQuestionAnswerEntity> findAllByUserIdAndStatus (long uid, Status status);
}
