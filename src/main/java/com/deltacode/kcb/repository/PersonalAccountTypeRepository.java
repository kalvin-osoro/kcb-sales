package com.deltacode.kcb.repository;

import com.deltacode.kcb.entity.PersonalAccountType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PersonalAccountTypeRepository extends JpaRepository<PersonalAccountType,Long> {
    List<PersonalAccountType> findAllByStatus(String status);
}
