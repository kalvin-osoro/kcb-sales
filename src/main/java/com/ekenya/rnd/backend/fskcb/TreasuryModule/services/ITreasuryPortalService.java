package com.ekenya.rnd.backend.fskcb.TreasuryModule.services;

import com.ekenya.rnd.backend.fskcb.TreasuryModule.models.reqs.*;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public interface ITreasuryPortalService {

    ArrayNode loadTargetsSummary(TreasurySummaryRequest model);

    ObjectNode loadRequestsSummary(TreasurySummaryRequest model);

    ObjectNode loadLeadsSummary(TreasurySummaryRequest model);

    ArrayNode loadNegotiationRequests();

    boolean approveNegotiationRequest();

    ArrayNode loadTradeRequests();

    ArrayNode loadAllLeads(TreasuryLeadsListRequest model);

    boolean assignLead(TreasuryAssignLeadRequest model);


    boolean createQuestionnaire(TreasuryAddQuestionnaireRequest model);

    ArrayNode loadAllQuestionnaires();

    boolean createTarget(TreasuryAddTargetRequest model);

    ArrayNode loadAllTargets();

    ArrayNode getAgentsInTarget(String targetId);

    ArrayNode attemptSyncTargetsWithCRM();
}
