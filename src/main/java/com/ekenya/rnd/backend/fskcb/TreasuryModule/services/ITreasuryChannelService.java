package com.ekenya.rnd.backend.fskcb.TreasuryModule.services;

import com.ekenya.rnd.backend.fskcb.AcquringModule.models.reqs.AcquiringNearbyCustomersRequest;
import com.ekenya.rnd.backend.fskcb.TreasuryModule.models.reqs.*;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public interface ITreasuryChannelService {

    ObjectNode targetsSummary();

    boolean attemptCreateLead(TreasuryAddLeadRequest model);

    ArrayNode loadDSRLeads(String dsrId);

    ArrayNode loadForexCounterRates();

    ArrayNode loadForexNegotiatedRates();

    boolean attemptCreateTradeRequest(TreasuryTradeRequest model);

    ArrayNode loadDSRTradeRequests();


    boolean attemptCreateNegotiationRequest(TreasuryNegRequest model);


    ArrayNode loadDSRNegotiationRequests();


    ObjectNode searchCustomer(String searchQuery);

    ObjectNode getNearbyCustomers(AcquiringNearbyCustomersRequest model);

    ObjectNode loadSummary();

    boolean attemptScheduleCustomerVisit(TreasuryCustomerVisitsRequest model);

    boolean attemptRescheduleCustomerVisit(TreasuryCustomerVisitsRequest model);


    ArrayNode loadCustomerVisits();


    ArrayNode loadCustomerVisitQuestionnaireResponses(TreasuryCustomerVisitQuestionnaireRequest model);

}
