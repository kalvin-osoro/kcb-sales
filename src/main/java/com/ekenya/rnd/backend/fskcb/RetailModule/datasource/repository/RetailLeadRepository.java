package com.ekenya.rnd.backend.fskcb.RetailModule.datasource.repository;

import com.ekenya.rnd.backend.fskcb.RetailModule.datasource.entites.RetailLeadEntity;
import com.ekenya.rnd.backend.fskcb.TreasuryModule.datasource.entities.TreasuryLeadEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RetailLeadRepository extends JpaRepository<RetailLeadEntity, Long> {
 @Query(value = "SELECT * FROM dbo_retail_leads WHERE dsr_id = ?1", nativeQuery = true)
    RetailLeadEntity[] findAllByDsrId(Long dsrId);

    @Query(value = "SELECT * FROM dbo_retail_leads where dsrId = ?1 AND assigned = false", nativeQuery = true)
    RetailLeadEntity[] findAllByDsrIdAndAssigned(Long dsrId);

    @Query(value = "SELECT * FROM dbo_retail_leads where dsrId = ?1 AND assigned = true", nativeQuery = true)
    RetailLeadEntity[] findAllAssignedLeadByDSRId(Long dsrId);
}
