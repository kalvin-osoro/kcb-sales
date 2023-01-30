package com.ekenya.rnd.backend.fskcb.DFSVoomaModule.services;

import com.ekenya.rnd.backend.fskcb.AcquringModule.models.AcquiringDSRsInTargetRequest;
import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.models.reqs.AssetByIdRequest;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.datasource.entities.DFSVoomaMerchantOnboardV1;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.models.reqs.*;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IVoomaPortalService {

    boolean scheduleCustomerVisit(VoomaCustomerVisitsRequest model);

    boolean reScheduleCustomerVisit(VoomaCustomerVisitsRequest voomaCustomerVisitsRequest);

    List<ObjectNode> getAllCustomerVisits();

    boolean assignLeadToDsr(VoomaAssignLeadRequest model);

    List<ObjectNode> getAllLeads();


    List<ObjectNode> getCustomerVisitQuestionnaireResponses(VoomaCustomerVisitQuestionnaireRequest voomaCustomerVisitQuestionnaireRequest);

    List<ObjectNode> getAllQuestionnaires();

    boolean createQuestionnaire(VoomaAddQuestionnaireRequest voomaAddQuestionnaireRequest);

//    List<ObjectNode> loadAllOnboardedMerchants();

    boolean approveMerchantOnboarding(DFSVoomaApproveMerchantOnboarindRequest dfsVoomaApproveMerchantOnboarindRequest);

    Object getMerchantById(VoomaMerchantDetailsRequest model);

    List<ObjectNode> getAllTargets();

    boolean createVoomaTarget(DFSVoomaAddTargetRequest voomaAddTargetRequest);


    List<ObjectNode> getOnboardingSummary();

    List<ObjectNode> getAllCustomerFeedbacks();

    ArrayNode getCustomerFeedbackResponses(DFSVoomaFeedBackRequestById model);

    List<ObjectNode> loadAllApprovedMerchants();

    boolean createAsset(String assetDetails, MultipartFile[] assetFiles);

    List<ObjectNode> getAllAssets();

    List<ObjectNode> getAllMerchantOnboardings();

    Object getCustomerVisitById(VoomaCustomerVisitsByIdRequest model);

    boolean assignTargetToDSR(DSRTAssignTargetRequest model);

    boolean assignTargetToTeam(TeamTAssignTargetRequest model);

    Object getTargetById(VoomaTargetByIdRequest model);
    List<?> getAllOnboardingV2();
    List<?> fetchAllAssetsV2();
    List<?> fetchAllLeadsV2();

    Object agentById(VoomaMerchantDetailsRequest model);

    boolean rejectMerchantOnboarding(DFSVoomaRejectMerchantOnboarindRequest model);

    ArrayNode getAllApprovedMerchantCoordinates();

    boolean rejectAgentOnboarding(DFSVoomaRejectMerchantOnboarindRequest model);

    boolean approveAgentOnboarding(DFSVoomaRejectMerchantOnboarindRequest model);

    ArrayNode getAllApprovedAgentCoordinates();

    boolean addQuestionnaire(QuestionnaireRequest model);

    List<ObjectNode> getAllAllQuestionnaireV1();

    List<ObjectNode> getOnboardingSummaryv1();


    Object getAssetById(AssetByIdRequest model);

    List<ObjectNode> getQuestionnaireResponses(GetRQuestionnaireRequest model);

    boolean disableQuestionnaire(GetRQuestionnaireRequest model);

    List<ObjectNode> salesPersonTarget(AcquiringDSRsInTargetRequest model);
}
