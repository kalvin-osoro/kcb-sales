package com.ekenya.rnd.backend.fskcb.PremiumSegmentModule.datasource.repositories;

import com.ekenya.rnd.backend.fskcb.PremiumSegmentModule.datasource.entity.PSQuestionerResponseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PSQuestionerResponseRepository extends JpaRepository<PSQuestionerResponseEntity, Long> {
    Iterable<? extends PSQuestionerResponseEntity> findAllByVisitIdAndQuestionId(Long visitId, Long questionId);
}
