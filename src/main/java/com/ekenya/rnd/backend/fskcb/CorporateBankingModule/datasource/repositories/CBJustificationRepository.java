package com.ekenya.rnd.backend.fskcb.CorporateBankingModule.datasource.repositories;

import com.ekenya.rnd.backend.fskcb.CorporateBankingModule.datasource.entities.CBJustificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CBJustificationRepository extends JpaRepository<CBJustificationEntity, Long> {
}
