package com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.repositories;

import com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.entities.AssetLogsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssetLogsRepository extends JpaRepository<AssetLogsEntity,Long> {
    List<AssetLogsEntity> findBySerialNumber(String serialNumber);
}
