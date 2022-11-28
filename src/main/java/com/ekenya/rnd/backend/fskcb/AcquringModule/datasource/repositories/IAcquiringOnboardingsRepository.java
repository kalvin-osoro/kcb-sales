package com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.repositories;

import com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.entities.AcquiringOnboardEntity;
import com.ekenya.rnd.backend.fskcb.PersonalBankingModule.datasource.entities.PSBankingOnboardingEntity;
import org.locationtech.jts.geom.Coordinate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface IAcquiringOnboardingsRepository extends JpaRepository<AcquiringOnboardEntity, Long> {

    @Query(value = "SELECT * FROM dbo_aqc_onboardings where created_on >= current_date at time zone 'UTC' - interval '7 days'", nativeQuery = true)
    AcquiringOnboardEntity[] fetchAllOnboardingCreatedLast7Days();
    @Query(value = "SELECT * FROM dbo_aqc_onboardings WHERE MATCH(merchant_name, business_name, nearly_land_mark) "
            + "AGAINST (?1)", nativeQuery = true)
    AcquiringOnboardEntity[] searchCustomers(String keyword);
//    @Query(value = "SELECT * FROM dbo_aqc_onboardings WHERE ST_DWithin(geom, ST_GeomFromText('POINT(?1 ?2)', 4326), ?3)", nativeQuery = true)
//    AcquiringOnboardEntity[] getNearbyCustomers(Coordinate coordinate, String name);
}
