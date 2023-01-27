package com.ekenya.rnd.backend.fskcb.DSRModule.datasource.repositories;

import com.ekenya.rnd.backend.fskcb.DSRModule.datasource.entities.SectorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ISectorRepository extends JpaRepository<SectorEntity,Long> {
}
