package com.ekenya.rnd.backend.fskcb.PremiumSegmentModule.datasource.repositories;

import com.ekenya.rnd.backend.fskcb.PremiumSegmentModule.datasource.entity.PSQuestionnaireQuestionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PSQuestionnaireQuestionRepository extends JpaRepository<PSQuestionnaireQuestionEntity, Long> {
}
