package com.ekenya.rnd.backend.fskcb.AgencyBankingModule.datasource.repositories;

import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.datasource.entities.AgencyAssetEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AgencyAssetRepository extends JpaRepository<AgencyAssetEntity, Long> {

    List<AgencyAssetEntity> findAllByAgentId(Long agentId);
}

