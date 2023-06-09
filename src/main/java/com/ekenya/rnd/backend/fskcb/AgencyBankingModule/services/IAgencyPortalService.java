package com.ekenya.rnd.backend.fskcb.AgencyBankingModule.services;

import com.ekenya.rnd.backend.fskcb.AcquringModule.models.AcquiringDSRsInTargetRequest;
import com.ekenya.rnd.backend.fskcb.AcquringModule.models.reqs.AcquiringApproveMerchant;
import com.ekenya.rnd.backend.fskcb.AcquringModule.models.reqs.AssignMerchant;
import com.ekenya.rnd.backend.fskcb.AcquringModule.models.reqs.DSRMerchantRequest;
import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.models.reqs.*;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.models.reqs.DSRTAssignTargetRequest;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.models.reqs.TeamTAssignTargetRequest;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.models.reqs.VoomaTargetByIdRequest;
import com.ekenya.rnd.backend.fskcb.TreasuryModule.models.reqs.TreasuryAssignLeadRequest;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IAgencyPortalService {

//    List<ObjectNode> getAllCustomerVisits();

    boolean scheduleCustomerVisit(AgencyCustomerVisitsRequest agencyCustomerVisitsRequest);

    boolean reScheduleCustomerVisit(AgencyRescheduleVisitsRequest assetManagementRequest);

    List<ObjectNode> getCustomerVisitQuestionnaireResponse(AgencyCustomerVisitQuestionnaireRequest agencyCustomerVisitQuestionnaireRequest);

    List<ObjectNode> getAllLeads();

    boolean createQuestionnaire(AgencyCollectAssetRequest.AgencyBankingQuestionnareQuestionRequest agencyBankingQuestionnareQuestionRequest);

    List<ObjectNode> getAllQuestionnaires();

    boolean createAgencyTarget(AgencyAddTargetRequest agencyAddTargetRequest);

    List<ObjectNode> loadAgencyTargets();

    List<ObjectNode> loadAllOnboardedAgents();

    boolean approveAgentOnboarding(AcquiringApproveMerchant model);

    List<ObjectNode> getAgentOnboardSummary(AgencySummaryRequest filters);

    boolean assignTargetToDSR(DSRTAssignTargetRequest model);

    boolean assignTargetToTeam(TeamTAssignTargetRequest model);

    Object getTargetById(VoomaTargetByIdRequest model);

    boolean rejectAgentOnboarding(AcquiringApproveMerchant model);

    List<?> getAllVisitsV2();

    boolean assignLead(TreasuryAssignLeadRequest model);

    List<ObjectNode> loadAllApprovedMerchants();

    boolean addAsset(String assetDetails, MultipartFile[] assetFiles);

    List<ObjectNode> getAllAssets();

    Object getAssetById(AssetByIdRequest model);

    ObjectNode attemptImportAgents(MultipartFile file);

    Object getAgentById(AgencyById model);

    Object getCustomerVisitById(AgencyVisitRequest model);

    boolean createVisitQuestion(AgencyCollectAssetRequest.AgencyBankingQuestionnareQuestionRequest model);

    boolean assignAgentToDSR(AssignMerchant model);

    List<ObjectNode> salesPersonTarget(AcquiringDSRsInTargetRequest model);

    List<ObjectNode> getDSRAgent(DSRMerchantRequest model);
}
