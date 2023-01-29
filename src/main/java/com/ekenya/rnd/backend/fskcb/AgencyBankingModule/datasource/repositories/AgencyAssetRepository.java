package com.ekenya.rnd.backend.fskcb.AgencyBankingModule.datasource.repositories;

import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.datasource.entities.AgencyAssetEntity;
import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.datasource.entities.AgencyOnboardingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AgencyAssetRepository extends JpaRepository<AgencyAssetEntity, Long> {


    Optional<Object> findBySerialNumber(String serialNumber);

    List<AgencyAssetEntity> findByAgentAccNumber(String agentAccNumber);


    //searchAgentByKeyWordAndStatus
}

