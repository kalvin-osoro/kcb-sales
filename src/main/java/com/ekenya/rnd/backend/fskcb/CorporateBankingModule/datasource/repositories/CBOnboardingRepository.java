package com.ekenya.rnd.backend.fskcb.CorporateBankingModule.datasource.repositories;

import com.ekenya.rnd.backend.fskcb.CorporateBankingModule.datasource.entities.CBOnboardingEntity;
import com.ekenya.rnd.backend.fskcb.PersonalBankingModule.datasource.entities.PSBankingOnboardingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CBOnboardingRepository extends JpaRepository<CBOnboardingEntity,Long> {
    @Query(value = "SELECT * FROM dbo_pb_onboarding where created_on >= current_date at time zone 'UTC' - interval '7 days'", nativeQuery = true)
    CBOnboardingEntity[] fetchAllOnboardingCreatedLast7Days();
}
