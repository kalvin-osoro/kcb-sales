package com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.repositories;

import com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.entities.AcquiringQuestionerResponseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AcquiringQuestionerResponseRepository extends JpaRepository<AcquiringQuestionerResponseEntity,Long> {
    AcquiringQuestionerResponseEntity findByVisitIdAndQuestionId(Long visitId, Long questionId);
}
