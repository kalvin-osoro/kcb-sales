package com.ekenya.rnd.backend.fskcb.AgencyBankingModule.datasource.repositories;

import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.datasource.entities.AgencyOnboardingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AgencyOnboardingRepository extends JpaRepository<AgencyOnboardingEntity, Long> {
    @Query(value = "SELECT * FROM agency_onboarding WHERE created_at >= DATE_SUB(NOW(), INTERVAL 7 DAY)", nativeQuery = true)
    AgencyOnboardingEntity[] fetchAllOnboardingCreatedLast7Days();


}
