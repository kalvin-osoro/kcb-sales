package com.ekenya.rnd.backend.fskcb.repository;//package ekenya.co.ke.frp_kcb.dao.repository;
import com.ekenya.rnd.backend.fskcb.entity.QuestionsType;
import com.ekenya.rnd.backend.fskcb.utils.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionsTypeRepository extends JpaRepository<QuestionsType, Long> {
    List<QuestionsType> findAllByStatus(Status status);
}
