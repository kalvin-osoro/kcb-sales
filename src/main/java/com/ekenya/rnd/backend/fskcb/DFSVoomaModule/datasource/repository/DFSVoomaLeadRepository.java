package com.ekenya.rnd.backend.fskcb.DFSVoomaModule.datasource.repository;

import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.datasource.entities.TargetType;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.datasource.entities.DFSVoomaLeadEntity;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.datasource.entities.DFSVoomaTargetEntity;
import com.ekenya.rnd.backend.fskcb.TreasuryModule.datasource.entities.TreasuryLeadEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DFSVoomaLeadRepository extends JpaRepository<DFSVoomaLeadEntity, Long> {
    @Query("SELECT l FROM DFSVoomaLeadEntity l WHERE l.dsrId = ?1")
    DFSVoomaLeadEntity[] findAllByDsrId(Long dsrId);

    @Query(value = "SELECT * FROM dbo_vooma_leads where dsrId = ?1 AND assigned = false", nativeQuery = true)
    DFSVoomaLeadEntity[] findAllByDsrIdAndAssigned(Long dsrId);

    @Query(value = "SELECT * FROM dbo_vooma_leads where dsrId = ?1 AND assigned = true", nativeQuery = true)
    DFSVoomaLeadEntity[] findAllAssignedLeadByDSRId(Long dsrId);

    @Query(value = "SELECT count(*) FROM dbo_vooma_leads where assigned = true AND dsrId = ?1", nativeQuery = true)
    int countTotalAssignedLeads(Long dsrId);
}
