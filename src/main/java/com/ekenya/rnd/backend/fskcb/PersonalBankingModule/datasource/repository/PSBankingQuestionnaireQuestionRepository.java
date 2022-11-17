package com.ekenya.rnd.backend.fskcb.PersonalBankingModule.datasource.repository;

import com.ekenya.rnd.backend.fskcb.PersonalBankingModule.datasource.entities.PSBankingQuestionnaireQuestionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PSBankingQuestionnaireQuestionRepository extends JpaRepository<PSBankingQuestionnaireQuestionEntity, Long> {
}
