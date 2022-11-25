package com.ekenya.rnd.backend.fskcb.AgencyBankingModule.datasource.repositories;

import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.datasource.entities.AgencyOnboardingKYCEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgencyOnboardingKYCRepository extends JpaRepository<AgencyOnboardingKYCEntity, Long> {
}
