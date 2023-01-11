package com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.repositories;

import com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.entities.AcquiringAssetFilesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AcquiringAssetFileRepository extends JpaRepository<AcquiringAssetFilesEntity, Long> {
    List<AcquiringAssetFilesEntity> findByIdAsset(Long assetId);
}
