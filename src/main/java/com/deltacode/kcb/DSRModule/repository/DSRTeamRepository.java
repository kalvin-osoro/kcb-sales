package com.deltacode.kcb.DSRModule.repository;

import com.deltacode.kcb.DSRModule.models.DSRTeam;
import com.deltacode.kcb.utils.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DSRTeamRepository extends JpaRepository<DSRTeam, Long> {
    List<DSRTeam> findDSRTeamByStatus(Status status);
    Boolean existsByTeamName(String teamName);
}
