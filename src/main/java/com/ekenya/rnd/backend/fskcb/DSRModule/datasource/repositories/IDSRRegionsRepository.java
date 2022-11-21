package com.ekenya.rnd.backend.fskcb.DSRModule.datasource.repositories;

import com.ekenya.rnd.backend.fskcb.DSRModule.datasource.entities.DSRAccountEntity;
import com.ekenya.rnd.backend.fskcb.DSRModule.datasource.entities.DSRRegionEntity;
import com.ekenya.rnd.backend.utils.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IDSRRegionsRepository extends JpaRepository<DSRRegionEntity, Long> {

    Optional<DSRRegionEntity> findByName(String name);

    List<DSRRegionEntity> findByStatus(Status status);
}
