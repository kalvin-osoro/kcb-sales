package com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.repositories;

import com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.entities.AcquiringOnboardEntity;
import com.ekenya.rnd.backend.fskcb.PersonalBankingModule.datasource.entities.PSBankingOnboardingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface IAcquiringOnboardingsRepository extends JpaRepository<AcquiringOnboardEntity, Long> {

    @Query(value = "SELECT * FROM dbo_aqc_onboardings where created_on >= current_date at time zone 'UTC' - interval '7 days'", nativeQuery = true)
    AcquiringOnboardEntity[] fetchAllOnboardingCreatedLast7Days();
    @Query(value = "SELECT * FROM dbo_aqc_onboardings where customer_name like %?1% or customer_phone_number like %?1%", nativeQuery = true)
    AcquiringOnboardEntity[] searchCustomers(String query);
}
