package com.ekenya.rnd.backend.fskcb.DSRModule.repository;

import com.ekenya.rnd.backend.fskcb.DSRModule.models.DSRTeam;
import com.ekenya.rnd.backend.fskcb.utils.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DSRTeamRepository extends JpaRepository<DSRTeam, Long> {
    List<DSRTeam> findDSRTeamByStatus(Status status);
    Boolean existsByTeamName(String teamName);
}
