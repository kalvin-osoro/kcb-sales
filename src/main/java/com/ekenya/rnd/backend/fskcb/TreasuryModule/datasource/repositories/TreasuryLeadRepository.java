package com.ekenya.rnd.backend.fskcb.TreasuryModule.datasource.repositories;

import com.ekenya.rnd.backend.fskcb.PersonalBankingModule.datasource.entities.PSBankingLeadEntity;
import com.ekenya.rnd.backend.fskcb.TreasuryModule.datasource.entities.TreasuryLeadEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TreasuryLeadRepository extends JpaRepository<TreasuryLeadEntity,Long> {
    @Query(value = "SELECT * FROM dbo_treasury_lead where created_on >= current_date at time zone 'UTC' - interval '7 days'", nativeQuery = true)
    PSBankingLeadEntity[] fetchAllLeadsCreatedLast7Days();

    @Query(value = "SELECT count(*) FROM dbo_treasury_lead where created_on >= current_date at time zone 'UTC' - interval '7 days'", nativeQuery = true)
    int countAllLeadsCreatedLast7Days();
    @Query(value = "SELECT count(*) FROM dbo_treasury_lead where created_on >= current_date at time zone 'UTC' - interval '7 days' AND assigned = true", nativeQuery = true)
    int countAllLeadsCreatedLast7DaysAssigned();
    @Query(value = "SELECT count(*) FROM dbo_treasury_lead where created_on >= current_date at time zone 'UTC' - interval '7 days' AND lead_status = 'OPEN'", nativeQuery = true)
    int countAllLeadsCreatedLast7DaysOpen();
    @Query(value = "SELECT count(*) FROM dbo_treasury_lead where created_on >= current_date at time zone 'UTC' - interval '7 days' AND lead_status = 'CLOSED'", nativeQuery = true)
    int countAllLeadsCreatedLast7DaysClosed();
    @Query(value = "SELECT count(*) FROM dbo_treasury_lead where created_on >= current_date at time zone 'UTC' - interval '7 days' AND priority = 'HOT'", nativeQuery = true)
    int countAllLeadsCreatedLast7DaysHot();
    @Query(value = "SELECT count(*) FROM dbo_treasury_lead where created_on >= current_date at time zone 'UTC' - interval '7 days' AND priority = 'WARM'", nativeQuery = true)
    int countAllLeadsCreatedLast7DaysWarm();
    @Query(value = "SELECT count(*) FROM dbo_treasury_lead where created_on >= current_date at time zone 'UTC' - interval '7 days' AND priority = 'COLD'", nativeQuery = true)
    int countAllLeadsCreatedLast7DaysCold();
    TreasuryLeadEntity[] findAllByDsrId(long dsrId);

//get all leads by dsrId and assigned false
    @Query(value = "SELECT * FROM dbo_treasury_lead where dsrId = ?1 AND assigned = false", nativeQuery = true)
    TreasuryLeadEntity[] findAllByDsrIdAndAssigned(Long dsrId);

    @Query(value = "SELECT * FROM dbo_treasury_lead where dsrId = ?1 AND assigned = true", nativeQuery = true)
    TreasuryLeadEntity[] findAllAssignedLeadByDSRId(Long dsrId);

    @Query(value = "SELECT count(*) FROM dbo_treasury_lead where assigned = true AND dsrId = ?1", nativeQuery = true)
    int countTotalAssignedLeads(Long dsrId);
}
