package com.ekenya.rnd.backend.fskcb.PersonalBankingModule.datasource.repository;

import com.ekenya.rnd.backend.fskcb.PersonalBankingModule.datasource.entities.PSBankingTargetEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PSBankingTargetRepository extends JpaRepository<PSBankingTargetEntity,Long> {
}
