package com.ekenya.rnd.backend.fskcb.CorporateBankingModule.services;

import com.ekenya.rnd.backend.fskcb.payload.CustomerAppointementRequest;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ICBService {

    List<?> getAllCustomerAppointment();
    boolean createCustomerAppointment( CustomerAppointementRequest customerAppointementRequest);
    boolean editCustomerAppointment( CustomerAppointementRequest customerAppointementRequest);
    boolean deleteCustomerAppointment( long id);
    List<?> getAllAppointmentByDsrId(long dsrId);
}
