package com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.repositories;

import com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.entities.AcquiringAssetEntity;
import com.ekenya.rnd.backend.fskcb.AcquringModule.models.AcquiringAssetResponse;
import com.ekenya.rnd.backend.fskcb.entity.Asset;
import com.ekenya.rnd.backend.fskcb.utils.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AcquiringAssetRepository extends JpaRepository<AcquiringAssetEntity, Long> {
}

