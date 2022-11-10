package com.ekenya.rnd.backend.fskcb.repository;


import com.ekenya.rnd.backend.fskcb.entity.QuestionnaireQuestion;
import com.ekenya.rnd.backend.fskcb.entity.UserAccType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionnaireQuestionRepository extends JpaRepository<QuestionnaireQuestion, Long> {
    List<QuestionnaireQuestion> findAllByUserAccountType(UserAccType userAccountType);
}
