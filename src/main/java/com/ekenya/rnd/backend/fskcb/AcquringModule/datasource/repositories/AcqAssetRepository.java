package com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.repositories;

import com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.entities.AcqAsset;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AcqAssetRepository extends JpaRepository<AcqAsset, Long> {
}
