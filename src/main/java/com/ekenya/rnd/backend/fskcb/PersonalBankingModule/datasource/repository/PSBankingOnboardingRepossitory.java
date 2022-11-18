package com.ekenya.rnd.backend.fskcb.PersonalBankingModule.datasource.repository;

import com.ekenya.rnd.backend.fskcb.PersonalBankingModule.datasource.entities.PSBankingOnboardingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PSBankingOnboardingRepossitory extends JpaRepository<PSBankingOnboardingEntity,Long> {
}
