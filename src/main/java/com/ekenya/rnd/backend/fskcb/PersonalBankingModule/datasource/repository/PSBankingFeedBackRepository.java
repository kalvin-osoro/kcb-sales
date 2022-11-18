package com.ekenya.rnd.backend.fskcb.PersonalBankingModule.datasource.repository;

import com.ekenya.rnd.backend.fskcb.PersonalBankingModule.datasource.entities.PSBankingFeedBackEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PSBankingFeedBackRepository extends JpaRepository<PSBankingFeedBackEntity,Long> {
}
