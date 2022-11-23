package com.ekenya.rnd.backend.fskcb.CorporateBankingModule.services;

import com.ekenya.rnd.backend.fskcb.CorporateBankingModule.models.reqs.CBAddLeadRequest;
import com.ekenya.rnd.backend.fskcb.payload.CustomerAppointementRequest;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.List;

public interface ICBChannelService {

//    List<?> getAllCustomerAppointment();
//    boolean createCustomerAppointment( CustomerAppointementRequest customerAppointementRequest);
//    boolean editCustomerAppointment( CustomerAppointementRequest customerAppointementRequest);
//    boolean deleteCustomerAppointment( long id);
//    List<?> getAllAppointmentByDsrId(long dsrId);

    boolean createCBLead(CBAddLeadRequest model);

    List<ObjectNode> getAllLeadsByDsrId(Long dsrId);
}
