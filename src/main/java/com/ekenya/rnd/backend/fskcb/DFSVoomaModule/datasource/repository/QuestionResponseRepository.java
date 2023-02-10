package com.ekenya.rnd.backend.fskcb.DFSVoomaModule.datasource.repository;

import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.datasource.entities.DFSVOOMAQuestionerResponseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionResponseRepository extends JpaRepository<DFSVOOMAQuestionerResponseEntity,Long> {
    List<DFSVOOMAQuestionerResponseEntity> findByQuestionnaireId(Long id);

    List<DFSVOOMAQuestionerResponseEntity> findByQuestionId(Long id);
}
