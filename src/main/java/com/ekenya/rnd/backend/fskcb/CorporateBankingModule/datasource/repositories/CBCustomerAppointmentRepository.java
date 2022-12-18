package com.ekenya.rnd.backend.fskcb.CorporateBankingModule.datasource.repositories;

import com.ekenya.rnd.backend.fskcb.CorporateBankingModule.datasource.entities.CBCustomerAppointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CBCustomerAppointmentRepository extends JpaRepository<CBCustomerAppointment, Long> {
    CBCustomerAppointment[] findAllByDsrIdAndAppointmentDate(Long dsrId, String appointmentDate);
}
