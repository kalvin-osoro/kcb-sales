package com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.repositories;

import com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.entities.AcquiringQuestionnaireQuestionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AcquiringQuestionnaireRepository extends JpaRepository<AcquiringQuestionnaireQuestionEntity, Long> {

}

