package com.ekenya.rnd.backend.fskcb.TreasuryModule.services;

import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.models.reqs.DSRTAssignTargetRequest;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.models.reqs.TeamTAssignTargetRequest;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.models.reqs.VoomaTargetByIdRequest;
import com.ekenya.rnd.backend.fskcb.TreasuryModule.models.reqs.*;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.List;

public interface ITreasuryPortalService {
    List<ObjectNode> getAllNegotiationReqs();

    List<ObjectNode> getAllTradeReqs();

    boolean approveTradeRequest(TreasuryApproveTradeRequest model);

    boolean approveNegotiationRequest(TreasuryAppveNegRequest model);

    boolean assignLead(TreasuryAssignLeadRequest model);

    List<ObjectNode> getAllLeads();

    boolean createQuestionnaire(TreasuryAddQuestionnaireRequest model);

    List<ObjectNode> getAllQuestionnaires();

    boolean createTarget(TreasuryAddTargetRequest model);

    List<ObjectNode> getAllTargets();

    boolean getDSRsInTarget(TreasuryDSRsInTargetRequest model);

    List<ObjectNode> getTargetsSummary(TreasurySummaryRequest filters);

    List<ObjectNode> getLeadsSummary(TreasurySummaryRequest filters);

    ObjectNode loadRequestsSummary(TreasurySummaryRequest filters);

    boolean assignTargetToDSR(DSRTAssignTargetRequest model);

    boolean assignTargetToTeam(TeamTAssignTargetRequest model);

    Object getTargetById(VoomaTargetByIdRequest model);

    boolean createCurrencyRate(TreasuryRateRequest model);

    List<?> getAllCurrencyRates();

    boolean editCurrencyRate(TreasuryRateRequest model);

    boolean updateCurrencyRate(TreasuryUpdateRequest model);
}
