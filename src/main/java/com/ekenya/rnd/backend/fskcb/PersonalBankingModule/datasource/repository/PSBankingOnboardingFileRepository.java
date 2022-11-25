package com.ekenya.rnd.backend.fskcb.PersonalBankingModule.datasource.repository;

import com.ekenya.rnd.backend.fskcb.PersonalBankingModule.datasource.entities.PSBankingOnboardingFileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PSBankingOnboardingFileRepository extends JpaRepository<PSBankingOnboardingFileEntity, Long> {
}
