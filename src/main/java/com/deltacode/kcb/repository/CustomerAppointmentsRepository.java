package com.deltacode.kcb.repository;

import com.deltacode.kcb.entity.CustomerAppointments;
import com.deltacode.kcb.utils.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CustomerAppointmentsRepository extends JpaRepository<CustomerAppointments, Long> {
    List<CustomerAppointments> findCustomerAppointmentsByStatus(Status status);
    List<CustomerAppointments> findCustomerAppointmentsByCreatedBy(long createdBy);

}
