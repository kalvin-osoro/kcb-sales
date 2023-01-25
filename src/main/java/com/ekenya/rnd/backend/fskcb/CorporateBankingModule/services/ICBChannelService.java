package com.ekenya.rnd.backend.fskcb.CorporateBankingModule.services;

import com.ekenya.rnd.backend.fskcb.CorporateBankingModule.models.CBGetLeadsByDsrIdRequest;
import com.ekenya.rnd.backend.fskcb.CorporateBankingModule.models.reqs.*;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.models.reqs.DSRSummaryRequest;
import com.ekenya.rnd.backend.fskcb.TreasuryModule.models.reqs.TreasuryAddLeadRequest;
import com.ekenya.rnd.backend.fskcb.TreasuryModule.models.reqs.TreasuryGetDSRLeads;
import com.ekenya.rnd.backend.fskcb.TreasuryModule.models.reqs.TreasuryUpdateLeadRequest;
import com.ekenya.rnd.backend.fskcb.payload.CustomerAppointementRequest;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.ArrayList;
import java.util.List;

public interface ICBChannelService {

//    List<?> getAllCustomerAppointment();
//    boolean createCustomerAppointment( CustomerAppointementRequest customerAppointementRequest);
//    boolean editCustomerAppointment( CustomerAppointementRequest customerAppointementRequest);
//    boolean deleteCustomerAppointment( long id);
//    List<?> getAllAppointmentByDsrId(long dsrId);



    boolean createCustomerVisit(CBCustomerVisitsRequest request);

    List<ObjectNode> getAllCustomerVisitsByDSR(CBCustomerVisitsRequest model);

    ArrayList<ObjectNode> getTargetsSummary();

    List<ObjectNode> getAllCustomerCovenants(CBCustomerConcession model);

    boolean createCustomerAppointment(CBAppointmentRequest model);

    List<ObjectNode> getCustomerAppointmentByDSRIdAndDate(CBAppointmentDateRequest model);

    boolean updateCustomerAppointment(CBAppointmentUpdateRequest model);

    boolean attemptCreateLead(TreasuryAddLeadRequest model);

    List<ObjectNode> loadDSRLead(TreasuryGetDSRLeads model);

    List<ObjectNode> loadAssignedDSRLead(TreasuryGetDSRLeads model);

    boolean attemptUpdateLead(TreasuryUpdateLeadRequest model);

    ArrayNode getDSRSummary(DSRSummaryRequest model);

    List<ObjectNode> getAllOpportunities();

    List<ObjectNode> getAllCustomeropportunityByDSR(CBDSROpportunity model);

    boolean updateOpportunity(CBChannelOpportunityRequest request);
}
