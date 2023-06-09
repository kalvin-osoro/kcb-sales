package com.ekenya.rnd.backend.fskcb.PremiumSegmentModule.services;

import com.ekenya.rnd.backend.fskcb.AcquringModule.models.AcquiringAddQuestionnaireRequest;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.models.reqs.DSRTAssignTargetRequest;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.models.reqs.TeamTAssignTargetRequest;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.models.reqs.VoomaTargetByIdRequest;
import com.ekenya.rnd.backend.fskcb.PremiumSegmentModule.models.reps.*;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.List;

public interface IPSPortalService {
    boolean addTrackedCovenant(PSAddConvenantRequest model);

    List<ObjectNode> getAllTrackedCovenants();

    boolean assignLead(PSAddLeadRequest model);

    List<ObjectNode> getAllLeads();

    boolean scheduleCustomerVisit(PSCustomerVisitsRequest model);

    boolean rescheduleCustomerVisit(PSCustomerVisitsRequest model);

    List<ObjectNode> getAllCustomerVisits();

    List<ObjectNode> getCustomerVisitQuestionnaireResponses(PSCustomerVisitQuestionnaireRequest model);

    boolean createQuestionnaire(AcquiringAddQuestionnaireRequest model);

    List<ObjectNode> loadQuestionnaires();

    List<ObjectNode> getAllOnboardings();

    boolean approveOnboarding(PSApproveMerchantOnboarindRequest model);

    List<ObjectNode> getAllCustomerFeedbacks();

    Object getCustomerFeedbackResponses(PSFeedBackRequest model);

    boolean createTarget(PSAddTargetRequest model);

    List<ObjectNode> getAllTargets();

    boolean assignTargetToDSR(DSRTAssignTargetRequest model);

    boolean assignTargetToTeam(TeamTAssignTargetRequest model);

    Object getTargetById(VoomaTargetByIdRequest model);
}
