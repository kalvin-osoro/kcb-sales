package com.ekenya.rnd.backend.fskcb.DFSVoomaModule.datasource.repository;

import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.datasource.entities.DFSVoomaQuestionerResponseEntity;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.datasource.entities.DFSVOOMAQuestionerResponseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DFSVoomaQuestionerResponseRepository extends JpaRepository<DFSVoomaQuestionerResponseEntity, Long> {
    @Query(value = "SELECT * FROM dfs_vooma_questionnaire_responses WHERE visit_id = ?1 AND question_id = ?2", nativeQuery = true)
    Iterable<? extends DFSVoomaQuestionerResponseEntity> findAllByVisitIdAndQuestionId(Long visitId, Long questionId);

}
