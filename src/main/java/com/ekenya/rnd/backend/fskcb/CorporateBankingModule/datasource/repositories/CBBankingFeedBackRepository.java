package com.ekenya.rnd.backend.fskcb.CorporateBankingModule.datasource.repositories;

import com.ekenya.rnd.backend.fskcb.CorporateBankingModule.datasource.entities.CBBankingFeedBackEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CBBankingFeedBackRepository extends JpaRepository<CBBankingFeedBackEntity, Long> {
}
