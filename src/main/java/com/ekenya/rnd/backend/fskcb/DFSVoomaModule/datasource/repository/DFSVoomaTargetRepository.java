package com.ekenya.rnd.backend.fskcb.DFSVoomaModule.datasource.repository;

import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.datasource.entities.DFSVoomaTargetEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DFSVoomaTargetRepository extends JpaRepository<DFSVoomaTargetEntity,Long> {
    @Query(value = "SELECT * FROM dbo_dsr_accounts WHERE id IN (SELECT dsr_id FROM dbo_dsr_targets WHERE target_id = ?1)",nativeQuery = true)
   List <DFSVoomaTargetEntity> getDsrInTarget(Long id);
}
