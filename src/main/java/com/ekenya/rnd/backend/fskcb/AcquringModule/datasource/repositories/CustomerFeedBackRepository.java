package com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.repositories;

import com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.entities.CustomerFeedBack;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerFeedBackRepository extends JpaRepository<CustomerFeedBack, Long> {
}

