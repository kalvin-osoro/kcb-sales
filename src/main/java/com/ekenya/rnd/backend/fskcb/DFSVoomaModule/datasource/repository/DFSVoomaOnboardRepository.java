package com.ekenya.rnd.backend.fskcb.DFSVoomaModule.datasource.repository;

import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.datasource.entities.DFSVoomaOnboardEntity;
import com.ekenya.rnd.backend.fskcb.PersonalBankingModule.datasource.entities.PSBankingOnboardingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DFSVoomaOnboardRepository extends JpaRepository<DFSVoomaOnboardEntity, Long> {
   //load all onboarding where createdOn is within the last 7 days
   @Query(value = "SELECT * FROM dbo_pb_onboarding where created_on >= current_date at time zone 'UTC' - interval '7 days'", nativeQuery = true)
    Iterable<DFSVoomaOnboardEntity> findAllByCreatedOn();

}