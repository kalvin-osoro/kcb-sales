package com.ekenya.rnd.backend.fskcb.CorporateBankingModule.services;

import com.ekenya.rnd.backend.fskcb.AcquringModule.models.AcquiringAddQuestionnaireRequest;
import com.ekenya.rnd.backend.fskcb.CorporateBankingModule.models.reqs.*;
import com.ekenya.rnd.backend.fskcb.RetailModule.models.reqs.RetailAddConcessionRequest;
import com.ekenya.rnd.backend.fskcb.payload.CustomerAppointementRequest;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.List;

public interface ICBPortalService {
    boolean assignLead(CBAssignLeadRequest model);

    List<ObjectNode> getAllLeads();

    boolean createQuestionnaire(AcquiringAddQuestionnaireRequest model);

    List<ObjectNode> getAllQuestionnaires();

    boolean scheduleCustomerVisit(CBCustomerVisitsRequest model);

    boolean rescheduleCustomerVisit(CBCustomerVisitsRequest model);

    List<ObjectNode> getAllCustomerVisits();

    List<ObjectNode> getAllCustomerFeedbacks();

    Object getCustomerFeedbackResponses(CBBankingFeedBackRequest model);

    boolean createCampain(CBCampaignsRequest model);

    List<ObjectNode> getAllCampaigns();

    boolean addConcession(RetailAddConcessionRequest model);

    List<ObjectNode> getAllConcessions();

    boolean addTrackedCovenant(CBAddConvenantRequest model);

    List<ObjectNode> getAllTrackedCovenants();

    List<ObjectNode> getOnboardingSummary();
}
