package com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.repositories;

import com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.entities.AcquiringAssetEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AcquiringAssetRepository extends JpaRepository<AcquiringAssetEntity, Long> {
}

