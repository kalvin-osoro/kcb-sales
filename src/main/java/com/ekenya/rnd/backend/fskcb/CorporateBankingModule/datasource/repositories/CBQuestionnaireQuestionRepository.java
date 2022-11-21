package com.ekenya.rnd.backend.fskcb.CorporateBankingModule.datasource.repositories;

import com.ekenya.rnd.backend.fskcb.CorporateBankingModule.datasource.entities.CBQuestionnaireQuestionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CBQuestionnaireQuestionRepository extends JpaRepository<CBQuestionnaireQuestionEntity, Long> {
}
