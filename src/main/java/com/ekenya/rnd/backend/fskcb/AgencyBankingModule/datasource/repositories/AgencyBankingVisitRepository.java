package com.ekenya.rnd.backend.fskcb.AgencyBankingModule.datasource.repositories;

import com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.entities.AcquiringCustomerVisitEntity;
import com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.entities.AcquiringQuestionnaireQuestionEntity;
import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.datasource.entities.AgencyBankingVisitEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AgencyBankingVisitRepository extends JpaRepository<AgencyBankingVisitEntity, Long> {
    @Query(value = "SELECT * FROM agency_bank_visit WHERE dsr_id=?1", nativeQuery = true)
    AgencyBankingVisitEntity[] getAllCustomerVisitsByDSR(int dsrId);

    @Query(value = "SELECT COUNT(*) FROM agency_bank_visit WHERE dsrId=?1", nativeQuery = true)
    int countTotalVisits(Long dsrId);


}
