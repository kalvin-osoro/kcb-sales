package com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.repositories;

import com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.entities.AcquiringOnboardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IAcquiringOnboardingsRepository extends JpaRepository<AcquiringOnboardEntity, Long> {
@Query(value = "SELECT * FROM dbo_aqc_onboardings WHERE created_on >= DATEADD(day, -7, GETDATE())", nativeQuery = true)//
    Iterable<? extends AcquiringOnboardEntity> fetchAllOnboardingCreatedLast7Days();
}
