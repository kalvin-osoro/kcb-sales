package com.ekenya.rnd.backend.fskcb.AgencyBankingModule.datasource.repositories;

import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.datasource.entities.AgencyOnboardingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AgencyOnboardingRepository extends JpaRepository<AgencyOnboardingEntity, Long> {
    @Query(value = "SELECT * FROM dbo_agency_bank_onboarding where created_on >= current_date at time zone 'UTC' - interval '7 days'", nativeQuery = true)
    AgencyOnboardingEntity[] fetchAllOnboardingCreatedLast7Days();




}