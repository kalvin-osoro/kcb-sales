package com.ekenya.rnd.backend.fskcb.setup.datasource.repository;

import com.ekenya.rnd.backend.fskcb.setup.datasource.entities.EmailEntities;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailRepository extends JpaRepository<EmailEntities,Long> {
    EmailEntities findByprofileCode(String profileCode);
}
