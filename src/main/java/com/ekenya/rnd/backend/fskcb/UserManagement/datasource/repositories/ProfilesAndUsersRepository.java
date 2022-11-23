package com.ekenya.rnd.backend.fskcb.UserManagement.datasource.repositories;

import com.ekenya.rnd.backend.fskcb.UserManagement.datasource.entities.ProfileAndUserEntity;
import com.ekenya.rnd.backend.utils.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProfilesAndUsersRepository  extends JpaRepository<ProfileAndUserEntity, Long> {

    List<ProfileAndUserEntity> findAllByUserId(long userId);
    List<ProfileAndUserEntity> findAllByProfileId(long profileId);

    Optional<ProfileAndUserEntity> findAllByUserIdAndProfileId(long userId, long profileId);

    Optional<ProfileAndUserEntity> findAllByUserIdAndProfileIdAndStatus(long userId, long profileId, Status status);

    List<ProfileAndUserEntity> findAllByProfileIdAndStatus(long profileId, Status status);
}
