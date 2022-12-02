package com.ekenya.rnd.backend.fskcb.AgencyBankingModule.services;

import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.datasource.entities.AgencyOnboardingEntity;
import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.models.reqs.*;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.models.reqs.DSRTAssignTargetRequest;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.models.reqs.TeamTAssignTargetRequest;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.models.reqs.VoomaTargetByIdRequest;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.List;

public interface IAgencyPortalService {

    List<ObjectNode> getAllCustomerVisits();

    boolean scheduleCustomerVisit(AgencyCustomerVisitsRequest agencyCustomerVisitsRequest);

    boolean reScheduleCustomerVisit(AgencyRescheduleVisitsRequest assetManagementRequest);

    List<ObjectNode> getCustomerVisitQuestionnaireResponse(AgencyCustomerVisitQuestionnaireRequest agencyCustomerVisitQuestionnaireRequest);

    List<ObjectNode> getAllLeads();

    boolean assignLeadToDsr(AgencyAssignLeadRequest agencyAssignLeadRequest, Long leadId);

    boolean createQuestionnaire(AgencyCollectAssetRequest.AgencyBankingQuestionnareQuestionRequest agencyBankingQuestionnareQuestionRequest);

    List<ObjectNode> getAllQuestionnaires();

    boolean createAgencyTarget(AgencyAddTargetRequest agencyAddTargetRequest);

    List<ObjectNode> loadAgencyTargets();

    List<ObjectNode> loadAllOnboardedAgents();

    boolean approveAgentOnboarding(AgencyOnboardingEntity agencyOnboardingEntity);

    List<ObjectNode> getAgentOnboardSummary(AgencySummaryRequest filters);

    boolean assignTargetToDSR(DSRTAssignTargetRequest model);

    boolean assignTargetToTeam(TeamTAssignTargetRequest model);

    Object getTargetById(VoomaTargetByIdRequest model);
}
