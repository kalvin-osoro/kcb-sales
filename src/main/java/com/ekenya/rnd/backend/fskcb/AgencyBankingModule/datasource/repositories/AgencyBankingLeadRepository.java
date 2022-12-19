package com.ekenya.rnd.backend.fskcb.AgencyBankingModule.datasource.repositories;

import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.datasource.entities.AgencyBankingLeadEntity;
import com.ekenya.rnd.backend.fskcb.TreasuryModule.datasource.entities.TreasuryLeadEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AgencyBankingLeadRepository extends JpaRepository<AgencyBankingLeadEntity,Long> {

    List<AgencyBankingLeadEntity> findAllByDsrId(Long dsrId);

    @Query(value = "SELECT * FROM agency_leads where dsrId = ?1 AND assigned = false", nativeQuery = true)
    AgencyBankingLeadEntity[] findAllByDsrIdAndAssigned(Long dsrId);

    @Query(value = "SELECT * FROM agency_leads where dsrId = ?1 AND assigned = true", nativeQuery = true)
    AgencyBankingLeadEntity[] findAllAssignedLeadByDSRId(Long dsrId);
}
