package com.ekenya.rnd.backend.fskcb.CorporateBankingModule.datasource.repositories;

import com.ekenya.rnd.backend.fskcb.CorporateBankingModule.datasource.entities.CBCustomerAppointmentRM;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CBCustomerAppointmentRMRepository extends JpaRepository<CBCustomerAppointmentRM,Long> {
}
