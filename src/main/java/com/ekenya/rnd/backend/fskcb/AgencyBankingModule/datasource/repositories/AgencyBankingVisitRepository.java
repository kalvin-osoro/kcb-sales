package com.ekenya.rnd.backend.fskcb.AgencyBankingModule.datasource.repositories;

import com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.entities.AcquiringQuestionnaireQuestionEntity;
import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.datasource.entities.AgencyBankingVisitEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgencyBankingVisitRepository extends JpaRepository<AgencyBankingVisitEntity, Long> {


}
