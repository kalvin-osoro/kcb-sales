package com.ekenya.rnd.backend.fskcb.UserManagement.datasource.repositories;

import com.ekenya.rnd.backend.fskcb.UserManagement.datasource.entities.ProfileUserEntity;
import com.ekenya.rnd.backend.fskcb.UserManagement.datasource.entities.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public interface ProfilesAndUsersRepository  extends JpaRepository<ProfileUserEntity, Long> {

}
