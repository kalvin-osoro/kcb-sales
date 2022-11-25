package com.ekenya.rnd.backend.fskcb.DFSVoomaModule.services;

import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.models.reqs.*;
import com.ekenya.rnd.backend.fskcb.payload.BusinessTypeDto;
import com.ekenya.rnd.backend.fskcb.payload.LiquidationResponse;
import com.ekenya.rnd.backend.fskcb.payload.LiquidationTypeDto;
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

    List<ObjectNode> getAllLeads(VoomaLeadsListRequest filters);


    List<ObjectNode> getCustomerVisitQuestionnaireResponses(VoomaCustomerVisitQuestionnaireRequest voomaCustomerVisitQuestionnaireRequest);

    List<ObjectNode> getAllQuestionnaires();

    boolean createQuestionnaire(VoomaAddQuestionnaireRequest voomaAddQuestionnaireRequest);

    List<ObjectNode> loadAllOnboardedMerchants(GetALLDSRMerchantOnboardingRequest model);

    boolean approveMerchantOnboarding(DFSVoomaApproveMerchantOnboarindRequest dfsVoomaApproveMerchantOnboarindRequest);

    Object getMerchantById(VoomaMerchantDetailsRequest model);

    List<ObjectNode> getAllTargets();

    boolean createVoomaTarget(DFSVoomaAddTargetRequest voomaAddTargetRequest);


    List<ObjectNode> getOnboardingSummary(VoomaSummaryRequest filters);

    List<ObjectNode> getAllCustomerFeedbacks();

    Object getCustomerFeedbackResponses(DFSVoomaFeedBackRequest dfsVoomaFeedBackRequest);
}
