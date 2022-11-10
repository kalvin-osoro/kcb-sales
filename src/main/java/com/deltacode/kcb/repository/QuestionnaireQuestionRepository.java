package com.deltacode.kcb.repository;


import com.deltacode.kcb.entity.QuestionnaireQuestion;
import com.deltacode.kcb.entity.UserAccType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionnaireQuestionRepository extends JpaRepository<QuestionnaireQuestion, Long> {
    List<QuestionnaireQuestion> findAllByUserAccountType(UserAccType userAccountType);
}
