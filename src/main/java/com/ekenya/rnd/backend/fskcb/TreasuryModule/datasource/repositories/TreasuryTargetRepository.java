package com.ekenya.rnd.backend.fskcb.TreasuryModule.datasource.repositories;

import com.ekenya.rnd.backend.fskcb.TreasuryModule.datasource.entities.TreasuryTargetEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TreasuryTargetRepository extends JpaRepository<TreasuryTargetEntity, Long> {
    @Query(value = "SELECT * FROM dbo_pb_targets where created_on >= current_date at time zone 'UTC' - interval '7 days'", nativeQuery = true)
    TreasuryTargetEntity[] fetchAllTargetsCreatedLast7Days();
}
