package com.ekenya.rnd.backend.fskcb.CorporateBankingModule.datasource.repositories;

import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.datasource.entities.TargetType;
import com.ekenya.rnd.backend.fskcb.CorporateBankingModule.datasource.entities.CBTargetEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CBTargetRepository extends JpaRepository<CBTargetEntity, Long> {

    @Query("SELECT t FROM CBTargetEntity t WHERE t.targetType = ?1")
    CBTargetEntity[] findAllByTargetType(TargetType targetType);

}

