package com.ekenya.rnd.backend.fskcb.CorporateBankingModule.datasource.repositories;

import com.ekenya.rnd.backend.fskcb.CorporateBankingModule.datasource.entities.QuestionnareQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionnareQuestionRepository extends JpaRepository<QuestionnareQuestion, Long> {

}
