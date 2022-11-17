package com.ekenya.rnd.backend.fskcb.PersonalBankingModule.datasource.repository;

import com.ekenya.rnd.backend.fskcb.PersonalBankingModule.datasource.entities.PSBankingVisitEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PSBankingCustomerVisitRepository extends JpaRepository<PSBankingVisitEntity, Long> {
}
