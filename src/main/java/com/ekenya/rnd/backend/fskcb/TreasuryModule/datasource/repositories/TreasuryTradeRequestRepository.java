package com.ekenya.rnd.backend.fskcb.TreasuryModule.datasource.repositories;

import com.ekenya.rnd.backend.fskcb.TreasuryModule.datasource.entities.TreasuryTradeRequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TreasuryTradeRequestRepository extends JpaRepository<TreasuryTradeRequestEntity, Long> {
   @Query(value = "SELECT * FROM dbo_treasury_trade_request where created_on >= current_date at time zone 'UTC' - interval '7 days'", nativeQuery = true)
    int countAllRequestsCreatedLast7Days();
    @Query(value = "SELECT count(*) FROM dbo_treasury_trade_request where created_on >= current_date at time zone 'UTC' - interval '7 days' AND status = 'OPEN'", nativeQuery = true)
    int countAllRequestsCreatedLast7DaysOpen();
@Query(value = "SELECT count(*) FROM dbo_treasury_trade_request where created_on >= current_date at time zone 'UTC' - interval '7 days' AND status = 'CLOSED'", nativeQuery = true)
    int countAllRequestsCreatedLast7DaysClosed();
    @Query(value = "SELECT count(*) FROM dbo_treasury_trade_request where created_on >= current_date at time zone 'UTC' - interval '7 days' AND assigned = true", nativeQuery = true)
    int countAllRequestsCreatedLast7DaysAssigned();
    @Query(value = "SELECT count(*) FROM dbo_treasury_trade_request where created_on >= current_date at time zone 'UTC' - interval '7 days' AND priority = 'HIGH'", nativeQuery = true)
    int countAllRequestsCreatedLast7DaysHigh();
@Query(value = "SELECT count(*) FROM dbo_treasury_trade_request where created_on >= current_date at time zone 'UTC' - interval '7 days' AND priority = 'MEDIUM'", nativeQuery = true)
    int countAllRequestsCreatedLast7DaysMedium();
@Query(value = "SELECT count(*) FROM dbo_treasury_trade_request where created_on >= current_date at time zone 'UTC' - interval '7 days' AND priority = 'LOW'", nativeQuery = true)
    int countAllRequestsCreatedLast7DaysLow();

    @Query(value = "SELECT * FROM dbo_treasury_trade_request where dsr_id = ?1", nativeQuery = true)
    TreasuryTradeRequestEntity[] findAllByDsrId(Long dsrId);


}
