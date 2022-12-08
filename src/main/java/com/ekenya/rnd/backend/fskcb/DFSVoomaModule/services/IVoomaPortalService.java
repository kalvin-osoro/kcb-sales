package com.ekenya.rnd.backend.fskcb.DFSVoomaModule.services;

import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.models.reqs.*;
import com.ekenya.rnd.backend.fskcb.payload.BusinessTypeDto;
import com.ekenya.rnd.backend.fskcb.payload.LiquidationResponse;
import com.ekenya.rnd.backend.fskcb.payload.LiquidationTypeDto;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
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

    ArrayNode loadAllApprovedMerchants();

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
}
