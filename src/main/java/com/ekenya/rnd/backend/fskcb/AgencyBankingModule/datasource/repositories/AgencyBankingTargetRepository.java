package com.ekenya.rnd.backend.fskcb.AgencyBankingModule.datasource.repositories;

import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.datasource.entities.AgencyBankingTargetEntity;
import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.datasource.entities.TargetType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AgencyBankingTargetRepository extends JpaRepository<AgencyBankingTargetEntity,Long> {
   @Query("SELECT t FROM AgencyBankingTargetEntity t WHERE t.targetType = ?1")
    AgencyBankingTargetEntity[] findAllByTargetType(TargetType targetType);
}
