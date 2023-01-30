package com.ekenya.rnd.backend.fskcb.DSRModule.datasource.repositories;

import com.ekenya.rnd.backend.fskcb.DSRModule.datasource.entities.DSRAccountEntity;
import com.ekenya.rnd.backend.fskcb.DSRModule.datasource.entities.DSRTeamEntity;
import com.ekenya.rnd.backend.utils.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IDSRTeamsRepository extends JpaRepository<DSRTeamEntity, Long> {
    List<DSRTeamEntity> findByStatus(Status status);
    Boolean existsByName(String name);
    Optional<DSRTeamEntity> findByName(String name);

    List<DSRTeamEntity> findAllByRegionId(long id);

}
