package com.ekenya.rnd.backend.fskcb.CorporateBankingModule.datasource.repositories;

import com.ekenya.rnd.backend.fskcb.CorporateBankingModule.datasource.entities.CBLeadEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ICBLeadsRepository extends JpaRepository<CBLeadEntity,Long> {
@Query("SELECT l FROM CBLeadEntity l WHERE l.dsrId = ?1")
    List<CBLeadEntity> findAllByDsrId(Long dsrId);
}
