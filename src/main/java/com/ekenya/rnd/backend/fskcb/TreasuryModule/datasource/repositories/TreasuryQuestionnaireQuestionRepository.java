package com.ekenya.rnd.backend.fskcb.TreasuryModule.datasource.repositories;

import com.ekenya.rnd.backend.fskcb.TreasuryModule.datasource.entities.TreasuryQuestionnaireQuestionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TreasuryQuestionnaireQuestionRepository extends JpaRepository<TreasuryQuestionnaireQuestionEntity, Long> {
}
