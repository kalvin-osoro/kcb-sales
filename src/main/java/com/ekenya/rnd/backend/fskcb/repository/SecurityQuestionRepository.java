package com.ekenya.rnd.backend.fskcb.repository;

import com.ekenya.rnd.backend.fskcb.entity.SecurityQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SecurityQuestionRepository extends JpaRepository<SecurityQuestion, Long> {

}
