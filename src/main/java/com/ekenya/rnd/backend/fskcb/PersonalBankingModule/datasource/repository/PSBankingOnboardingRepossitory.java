package com.ekenya.rnd.backend.fskcb.PersonalBankingModule.datasource.repository;

import com.ekenya.rnd.backend.fskcb.PersonalBankingModule.datasource.entities.PSBankingOnboardingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PSBankingOnboardingRepossitory extends JpaRepository<PSBankingOnboardingEntity,Long> {
   @Query(value = "SELECT * FROM dbo_pb_onboarding where created_on >= current_date at time zone 'UTC' - interval '7 days'", nativeQuery = true)
    PSBankingOnboardingEntity[] fetchAllOnboardingCreatedLast7Days();
}
