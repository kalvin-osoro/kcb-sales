package com.ekenya.rnd.backend.fskcb.PersonalBankingModule.datasource.repository;

import com.ekenya.rnd.backend.fskcb.PersonalBankingModule.datasource.entities.PSBankingTargetEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PSBankingTargetRepository extends JpaRepository<PSBankingTargetEntity,Long> {
    @Query(value = "SELECT * FROM dbo_pb_targets where created_on >= current_date at time zone 'UTC' - interval '7 days'", nativeQuery = true)
    PSBankingTargetEntity[] fetchAllTargetsCreatedLast7Days();
}
