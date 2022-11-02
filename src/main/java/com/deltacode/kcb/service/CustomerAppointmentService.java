package com.deltacode.kcb.service;


import com.deltacode.kcb.payload.CustomerAppointementRequest;
import org.springframework.http.ResponseEntity;

public interface CustomerAppointmentService {
    ResponseEntity<?> getAllCustomerAppointment();
    ResponseEntity<?> createCustomerAppointment( CustomerAppointementRequest customerAppointementRequest);
    ResponseEntity<?> editCustomerAppointment( CustomerAppointementRequest customerAppointementRequest);
    ResponseEntity<?> deleteCustomerAppointment( long id);
    ResponseEntity<?> getAllAppointmentByDsrId(long dsrId);
}
