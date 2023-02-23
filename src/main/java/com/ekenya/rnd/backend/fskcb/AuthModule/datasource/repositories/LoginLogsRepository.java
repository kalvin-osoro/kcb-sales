package com.ekenya.rnd.backend.fskcb.AuthModule.datasource.repositories;

import com.ekenya.rnd.backend.fskcb.AuthModule.datasource.entities.LoginLogs;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginLogsRepository extends JpaRepository<LoginLogs,Long> {

}
