package com.ekenya.rnd.backend.fskcb.repository;

import com.ekenya.rnd.backend.fskcb.entity.PersonalAccountType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PersonalAccountTypeRepository extends JpaRepository<PersonalAccountType,Long> {
    List<PersonalAccountType> findAllByStatus(String status);
}
