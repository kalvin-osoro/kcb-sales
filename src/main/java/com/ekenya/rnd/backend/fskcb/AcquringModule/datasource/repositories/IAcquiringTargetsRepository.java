package com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.repositories;

import com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.entities.AcquiringLeadEntity;
import com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.entities.AcquiringTargetEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IAcquiringTargetsRepository  extends JpaRepository<AcquiringTargetEntity, Long> {
    @Query(value = "SELECT * FROM dbo_aqc_targets WHERE created_on >= DATEADD(day, -7, GETDATE())", nativeQuery = true)//
    List<AcquiringTargetEntity> fetchAllTargetCreatedLast7Days();

}
