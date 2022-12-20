package com.ekenya.rnd.backend.fskcb.AgencyBankingModule.datasource.repositories;

import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.datasource.entities.AgencyBankingLeadEntity;
import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.datasource.entities.AgencyOnboardingEntity;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.datasource.entities.DFSVoomaMerchantOnboardV1;
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

    @Query(value = "SELECT * FROM dbo_agency_bank_onboarding WHERE isApproved = true", nativeQuery = true)
    Iterable<AgencyOnboardingEntity> findAllByIsApproved();
}
