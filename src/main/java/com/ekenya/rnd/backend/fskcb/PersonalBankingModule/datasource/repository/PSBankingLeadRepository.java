package com.ekenya.rnd.backend.fskcb.PersonalBankingModule.datasource.repository;

import com.ekenya.rnd.backend.fskcb.PersonalBankingModule.datasource.entities.PSBankingLeadEntity;
import com.ekenya.rnd.backend.fskcb.TreasuryModule.datasource.entities.TreasuryLeadEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PSBankingLeadRepository extends JpaRepository<PSBankingLeadEntity, Long> {
    @Query(value = "SELECT * FROM psbanking_leads where created_on >= current_date at time zone 'UTC' - interval '7 days'", nativeQuery = true)
    PSBankingLeadEntity[] fetchAllLeadsCreatedLast7Days();
    
    //count all leads created last 7 days
    @Query(value = "SELECT count(*) FROM psbanking_leads where created_on >= current_date at time zone 'UTC' - interval '7 days'", nativeQuery = true)
    int countAllLeadsCreatedLast7Days();
    @Query(value = "SELECT count(*) FROM psbanking_leads where created_on >= current_date at time zone 'UTC' - interval '7 days' AND assigned = true", nativeQuery = true)
    int countAllLeadsCreatedLast7DaysAssigned();
    @Query(value = "SELECT count(*) FROM psbanking_leads where created_on >= current_date at time zone 'UTC' - interval '7 days' AND lead_status = 'OPEN'", nativeQuery = true)
    int countAllLeadsCreatedLast7DaysOpen();
    @Query(value = "SELECT count(*) FROM psbanking_leads where created_on >= current_date at time zone 'UTC' - interval '7 days' AND lead_status = 'CLOSED'", nativeQuery = true)
    int countAllLeadsCreatedLast7DaysClosed();
    @Query(value = "SELECT count(*) FROM psbanking_leads where created_on >= current_date at time zone 'UTC' - interval '7 days' AND priority = 'HOT'", nativeQuery = true)
    int countAllLeadsCreatedLast7DaysHot();
    @Query(value = "SELECT count(*) FROM psbanking_leads where created_on >= current_date at time zone 'UTC' - interval '7 days' AND priority = 'WARM'", nativeQuery = true)
    int countAllLeadsCreatedLast7DaysWarm();
    @Query(value = "SELECT count(*) FROM psbanking_leads where created_on >= current_date at time zone 'UTC' - interval '7 days' AND priority = 'COLD'", nativeQuery = true)
    int countAllLeadsCreatedLast7DaysCold();
    @Query(value = "SELECT * FROM psbanking_leads where dsr_id = ?1", nativeQuery = true)
    PSBankingLeadEntity[] findAllByDsrId(Long dsrId);

    @Query(value = "SELECT * FROM psbanking_leads where dsrId = ?1 AND assigned = false", nativeQuery = true)
    PSBankingLeadEntity[] findAllByDsrIdAndAssigned(Long dsrId);

    @Query(value = "SELECT * FROM psbanking_leads where dsrId = ?1 AND assigned = true", nativeQuery = true)
    PSBankingLeadEntity[] findAllAssignedLeadByDSRId(Long dsrId);

    @Query(value = "SELECT count(*) FROM psbanking_leads where assigned = true AND dsrId = ?1", nativeQuery = true)
    int countTotalAssignedLeads(Long dsrId);
}
