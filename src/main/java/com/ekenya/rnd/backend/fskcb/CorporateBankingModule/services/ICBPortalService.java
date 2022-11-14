package com.ekenya.rnd.backend.fskcb.CorporateBankingModule.services;

import com.ekenya.rnd.backend.fskcb.payload.CustomerAppointementRequest;

import java.util.List;

public interface ICBPortalService {

    List<?> getAllCustomerAppointment();
    boolean createCustomerAppointment( CustomerAppointementRequest customerAppointementRequest);
    boolean editCustomerAppointment( CustomerAppointementRequest customerAppointementRequest);
    boolean deleteCustomerAppointment( long id);
    List<?> getAllAppointmentByDsrId(long dsrId);
}
