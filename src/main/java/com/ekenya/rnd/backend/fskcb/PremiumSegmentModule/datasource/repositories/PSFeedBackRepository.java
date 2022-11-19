package com.ekenya.rnd.backend.fskcb.PremiumSegmentModule.datasource.repositories;

import com.ekenya.rnd.backend.fskcb.PremiumSegmentModule.datasource.entity.PSFeedBackEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PSFeedBackRepository extends JpaRepository<PSFeedBackEntity,Long> {
}
