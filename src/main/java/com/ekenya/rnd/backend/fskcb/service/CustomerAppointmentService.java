package com.ekenya.rnd.backend.fskcb.service;


import com.ekenya.rnd.backend.fskcb.payload.CustomerAppointementRequest;
import org.springframework.http.ResponseEntity;

public interface CustomerAppointmentService {
    ResponseEntity<?> getAllCustomerAppointment();
    ResponseEntity<?> createCustomerAppointment( CustomerAppointementRequest customerAppointementRequest);
    ResponseEntity<?> editCustomerAppointment( CustomerAppointementRequest customerAppointementRequest);
    ResponseEntity<?> deleteCustomerAppointment( long id);
    ResponseEntity<?> getAllAppointmentByDsrId(long dsrId);
}
