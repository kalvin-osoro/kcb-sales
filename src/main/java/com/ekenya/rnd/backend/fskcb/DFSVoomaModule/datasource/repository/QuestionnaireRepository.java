package com.ekenya.rnd.backend.fskcb.DFSVoomaModule.datasource.repository;

import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.datasource.entities.QuestionnaireEntity;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.datasource.entities.QuestionnaireType;
import com.ekenya.rnd.backend.utils.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface QuestionnaireRepository extends JpaRepository<QuestionnaireEntity,Long> {
    Iterable<? extends QuestionnaireEntity> findByQuestionnaireTypeAndProfileCodeAndStatus(QuestionnaireType questionnaireType, String profileCode, Status active);
//    @Query("SELECT q FROM QuestionnaireEntity q WHERE q.questionnaireType = ?1 AND q.status = 'ACTIVE'")
    QuestionnaireEntity findByQuestionnaireTypeAndStatus(QuestionnaireType questionnaireType, Status active);

    QuestionnaireEntity findByQuestionnaireTypeAndStatusAndProfileCode(QuestionnaireType questionnaireType, Status active, String profileCode);
}