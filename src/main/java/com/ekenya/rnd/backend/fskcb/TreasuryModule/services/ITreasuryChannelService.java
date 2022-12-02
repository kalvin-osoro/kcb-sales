package com.ekenya.rnd.backend.fskcb.TreasuryModule.services;

import com.ekenya.rnd.backend.fskcb.AcquringModule.models.reqs.AcquiringNearbyCustomersRequest;
import com.ekenya.rnd.backend.fskcb.TreasuryModule.models.reqs.*;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.List;

public interface ITreasuryChannelService {


    boolean attemptCreateLead(TreasuryAddLeadRequest model);

    List<ObjectNode> loadDSRLead(TreasuryGetDSRLeads model);

    ArrayNode getForexRates();

    ArrayNode loadForexCounterRates();

    boolean attemptCreateTradeRequest(TreasuryTradeRequest model);

    List<ObjectNode> loadAllDSRTradeReqs(TreasuryGetDSRTradeRequest model);

    boolean attemptCreateNegotiationRequest(TreasuryNegRequest request);

    ArrayNode loadDSRNegotiationRequests();

    ObjectNode targetsSummary();

    boolean attemptScheduleCustomerVisit(TreasuryCustomerVisitsRequest request);

    boolean attemptRescheduleCustomerVisit(TreasuryCustomerVisitsRequest request);

    ArrayNode loadCustomerVisits();

    ArrayNode loadCustomerVisitQuestionnaireResponses(TreasuryCustomerCallReportRequest request);

    ObjectNode searchCustomer(String query);

    ObjectNode getNearbyCustomers(AcquiringNearbyCustomersRequest request);

    ObjectNode loadSummary();

    boolean createCallReport(TreasuryCustomerCallReportRequest model);

    List<ObjectNode> loadAssignedDSRLead(TreasuryGetDSRLeads model);

    boolean attemptUpdateLead(TreasuryUpdateLeadRequest model);
}
