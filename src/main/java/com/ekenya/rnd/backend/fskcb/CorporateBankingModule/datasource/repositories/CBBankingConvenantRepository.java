package com.ekenya.rnd.backend.fskcb.CorporateBankingModule.datasource.repositories;

import com.ekenya.rnd.backend.fskcb.CorporateBankingModule.datasource.entities.CBBankingConvenantEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CBBankingConvenantRepository extends JpaRepository<CBBankingConvenantEntity, Long> {
    CBBankingConvenantEntity[] findByCustomerId(String customerId);
}
