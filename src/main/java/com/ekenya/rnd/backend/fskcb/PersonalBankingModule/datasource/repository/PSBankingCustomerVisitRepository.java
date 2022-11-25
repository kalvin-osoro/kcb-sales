package com.ekenya.rnd.backend.fskcb.PersonalBankingModule.datasource.repository;

import com.ekenya.rnd.backend.fskcb.PersonalBankingModule.datasource.entities.PSBankingVisitEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PSBankingCustomerVisitRepository extends JpaRepository<PSBankingVisitEntity, Long> {
    @Query(value = "SELECT * FROM psbanking_visits where created_on >= current_date at time zone 'UTC' - interval '7 days'", nativeQuery = true)
    PSBankingVisitEntity[] fetchAllVisitsCreatedLast7Days();
@Query(value = "SELECT * FROM psbanking_visits where dsr_id = ?1", nativeQuery = true)
    List<PSBankingVisitEntity> findAllByDsrId(Long dsrId);
}
