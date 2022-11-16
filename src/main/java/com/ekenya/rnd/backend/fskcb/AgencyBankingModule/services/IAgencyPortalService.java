package com.ekenya.rnd.backend.fskcb.AgencyBankingModule.services;

import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.models.reqs.AgencyAssignLeadRequest;
import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.models.reqs.AgencyCustomerVisitQuestionnaireRequest;
import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.models.reqs.AgencyCustomerVisitsRequest;
import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.models.reqs.AgencyLeadRequest;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.List;

public interface IAgencyPortalService {

    List<ObjectNode> getAllCustomerVisits();

    boolean scheduleCustomerVisit(AgencyCustomerVisitsRequest agencyCustomerVisitsRequest);

    boolean reScheduleCustomerVisit(AgencyCustomerVisitsRequest assetManagementRequest);

    List<ObjectNode> getCustomerVisitQuestionnaireResponse(AgencyCustomerVisitQuestionnaireRequest agencyCustomerVisitQuestionnaireRequest);

    List<ObjectNode> getAllLeads();

    boolean assignLeadToDsr(AgencyAssignLeadRequest agencyAssignLeadRequest, Long leadId);
}
