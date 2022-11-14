package com.ekenya.rnd.backend.fskcb.DSRModule.datasource.repositories;

import com.ekenya.rnd.backend.fskcb.DSRModule.models.DSRTeam;
import com.ekenya.rnd.backend.utils.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DSRTeamRepository extends JpaRepository<DSRTeam, Long> {
    List<DSRTeam> findDSRTeamByStatus(Status status);
    Boolean existsByTeamName(String teamName);
}
