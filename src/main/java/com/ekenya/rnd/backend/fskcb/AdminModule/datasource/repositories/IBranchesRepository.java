package com.ekenya.rnd.backend.fskcb.AdminModule.datasource.repositories;

import com.ekenya.rnd.backend.fskcb.AdminModule.datasource.entities.BranchEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IBranchesRepository extends JpaRepository<BranchEntity,Long> {

    Boolean existsByName(String name);

    Boolean existsByCode(String code);
}
