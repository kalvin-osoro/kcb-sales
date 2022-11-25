package com.ekenya.rnd.backend.fskcb.AgencyBankingModule.datasource.repositories;

import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.datasource.entities.AgencyAssetFilesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgencyAssetFilesRepository extends JpaRepository<AgencyAssetFilesEntity, Long> {
}

