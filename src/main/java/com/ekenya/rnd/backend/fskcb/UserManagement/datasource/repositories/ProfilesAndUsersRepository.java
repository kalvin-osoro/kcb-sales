package com.ekenya.rnd.backend.fskcb.UserManagement.datasource.repositories;

import com.ekenya.rnd.backend.fskcb.UserManagement.datasource.entities.ProfileUserEntity;
import com.ekenya.rnd.backend.fskcb.UserManagement.datasource.entities.UserRole;
import com.ekenya.rnd.backend.utils.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.List;
import java.util.Optional;

public interface ProfilesAndUsersRepository  extends JpaRepository<ProfileUserEntity, Long> {

    List<ProfileUserEntity> findAllByUserId(long userId);

    Optional<ProfileUserEntity> findAllByUserIdAndProfileId(long userId, long profileId);

    Optional<ProfileUserEntity> findAllByUserIdAndProfileIdAndStatus(long userId, long profileId, Status status);
}
