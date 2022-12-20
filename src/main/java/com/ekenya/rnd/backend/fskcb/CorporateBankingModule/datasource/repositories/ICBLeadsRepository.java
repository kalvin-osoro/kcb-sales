package com.ekenya.rnd.backend.fskcb.CorporateBankingModule.datasource.repositories;

import com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.entities.AcquiringLeadEntity;
import com.ekenya.rnd.backend.fskcb.CorporateBankingModule.datasource.entities.CBLeadEntity;
import com.ekenya.rnd.backend.fskcb.TreasuryModule.datasource.entities.TreasuryLeadEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ICBLeadsRepository extends JpaRepository<CBLeadEntity,Long> {
@Query("SELECT l FROM CBLeadEntity l WHERE l.dsrId = ?1")
    List<CBLeadEntity> findAllByDsrId(Long dsrId);

    @Query(value = "SELECT * FROM dbo_cb_leads where dsrId = ?1 AND assigned = false", nativeQuery = true)
    CBLeadEntity[] findAllByDsrIdAndAssigned(Long dsrId);

    @Query(value = "SELECT * FROM dbo_cb_leads where dsrId = ?1 AND assigned = true", nativeQuery = true)
    CBLeadEntity[] findAllAssignedLeadByDSRId(Long dsrId);

//    @Query(value = "SELECT * FROM dbo_cb_leads where dsrId = ?1 AND assigned = false", nativeQuery = true)
//    AcquiringLeadEntity[] findAllByDsrIdAndAssigned(Long dsrId);
    @Query(value = "SELECT count(*) FROM dbo_cb_leads where assigned = true AND dsrId = ?1", nativeQuery = true)
    int countTotalAssignedLeads(Long dsrId);
}
