package com.deltacode.kcb.repository;//package ekenya.co.ke.frp_kcb.dao.repository;
import com.deltacode.kcb.entity.QuestionsType;
import com.deltacode.kcb.utils.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionsTypeRepository extends JpaRepository<QuestionsType, Long> {
    List<QuestionsType> findAllByStatus(Status status);
}
