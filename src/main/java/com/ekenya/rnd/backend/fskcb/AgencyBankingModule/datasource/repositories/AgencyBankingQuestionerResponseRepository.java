package com.ekenya.rnd.backend.fskcb.AgencyBankingModule.datasource.repositories;

import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.datasource.entities.AgencyBankingQuestionerResponseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgencyBankingQuestionerResponseRepository extends JpaRepository<AgencyBankingQuestionerResponseEntity,Long> {
    Iterable<? extends AgencyBankingQuestionerResponseEntity> findAllByVisitIdAndQuestionId(Long visitId, Long questionId);
}
