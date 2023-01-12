package com.ekenya.rnd.backend.fskcb.AgencyBankingModule.datasource.repositories;

import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.datasource.entities.AgencyBankingVisitFileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AgencyBankingVisitFileRepository extends JpaRepository<AgencyBankingVisitFileEntity, Long> {

    List<AgencyBankingVisitFileEntity> findByIdVisit(Long visitId);
}
