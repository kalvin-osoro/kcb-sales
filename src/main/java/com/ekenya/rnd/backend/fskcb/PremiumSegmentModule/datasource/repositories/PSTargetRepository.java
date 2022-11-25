package com.ekenya.rnd.backend.fskcb.PremiumSegmentModule.datasource.repositories;

import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.datasource.entities.TargetType;
import com.ekenya.rnd.backend.fskcb.PersonalBankingModule.datasource.entities.PSBankingTargetEntity;
import com.ekenya.rnd.backend.fskcb.PremiumSegmentModule.datasource.entity.PSTargetEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PSTargetRepository extends JpaRepository<PSTargetEntity, Long> {
   @Query(name = "SELECT * FROM dbo_pb_targets WHERE target_type = ? 1",nativeQuery = true)
   PSTargetEntity[] findAllByTargetType(TargetType targetType);
}
