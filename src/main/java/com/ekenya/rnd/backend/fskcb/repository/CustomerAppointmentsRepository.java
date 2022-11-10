package com.ekenya.rnd.backend.fskcb.repository;

import com.ekenya.rnd.backend.fskcb.entity.CustomerAppointments;
import com.ekenya.rnd.backend.fskcb.utils.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CustomerAppointmentsRepository extends JpaRepository<CustomerAppointments, Long> {
    List<CustomerAppointments> findCustomerAppointmentsByStatus(Status status);
    List<CustomerAppointments> findCustomerAppointmentsByCreatedBy(long createdBy);

}
