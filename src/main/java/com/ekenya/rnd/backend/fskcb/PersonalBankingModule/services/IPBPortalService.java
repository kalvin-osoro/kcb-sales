package com.ekenya.rnd.backend.fskcb.PersonalBankingModule.services;

import com.ekenya.rnd.backend.fskcb.AcquringModule.models.AcquiringAddQuestionnaireRequest;
import com.ekenya.rnd.backend.fskcb.AcquringModule.models.AcquiringDSRsInTargetRequest;
import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.models.reqs.AgencyById;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.models.reqs.DSRTAssignTargetRequest;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.models.reqs.TeamTAssignTargetRequest;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.models.reqs.VoomaTargetByIdRequest;
import com.ekenya.rnd.backend.fskcb.PersonalBankingModule.datasource.entities.PSBankingOnboardingEntity;
import com.ekenya.rnd.backend.fskcb.PersonalBankingModule.models.reqs.*;
import com.ekenya.rnd.backend.fskcb.PremiumSegmentModule.models.reps.PSApproveMerchantOnboarindRequest;
import com.ekenya.rnd.backend.fskcb.payload.PersonalAccountTypeRequest;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IPBPortalService {




    List<ObjectNode> getAllLeads();

    boolean assignLead(PBAssignLeadRequest model);

    boolean scheduleCustomerVisit(PBCustomerVisitsRequest model);

    boolean rescheduleCustomerVisit(PBCustomerVisitsRequest model);

    List<ObjectNode> getAllCustomerVisits();

    List<ObjectNode> getCustomerVisitQuestionnaireResponses(PBCustomerVisitQuestionnaireRequest assetManagementRequest);

    boolean createQuestionnaire(PSBankingAddQuestionnaireRequest model);

    List<ObjectNode> getAllQuestionnaires();

    boolean createPBTarget(PBAddTargetRequest assetManagementRequest);

    List<ObjectNode> getAllTargets();

    List<ObjectNode> getAllOnboardings();

    boolean approveOnboarding(PSBankingOnboardingEntity model);

    List<ObjectNode> getOnboardingSummary(PBSummaryRequest filters);

    List<ObjectNode> getCustomerVisitsSummary(PBSummaryRequest filters);

    List<ObjectNode> getTargetsSummary(PBSummaryRequest filters);

    List<ObjectNode> getLeadsSummary(PBSummaryRequest filters);

    List<ObjectNode> getAllCustomerFeedbacks();

    Object getCustomerFeedbackResponses(PSBankingFeedBackRequest psBankingFeedBackRequest);

    boolean assignTargetToDSR(DSRTAssignTargetRequest model);

    boolean assignTargetToTeam(TeamTAssignTargetRequest model);

    Object getTargetById(VoomaTargetByIdRequest model);

    Object getCustomerById(AgencyById model);

    List<ObjectNode> salesPersonTarget(AcquiringDSRsInTargetRequest model);
}
