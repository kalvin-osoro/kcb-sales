package com.ekenya.rnd.backend.fskcb.TreasuryModule.datasource.repositories;

import com.ekenya.rnd.backend.fskcb.TreasuryModule.datasource.entities.TreasuryCallReportEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TreasuryCallReportRepository extends JpaRepository<TreasuryCallReportEntity,Long> {

    @Query(value = "SELECT COUNT(*) FROM dbo_treasury_call_report WHERE dsrId=?1", nativeQuery = true)
    int countTotalVisits(Long dsrId);
}
