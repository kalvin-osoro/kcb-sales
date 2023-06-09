package com.ekenya.rnd.backend.fskcb.AcquringModule.services;

import com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.entities.AcquiringLeadEntity;
import com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.entities.AcquiringOnboardEntity;
import com.ekenya.rnd.backend.fskcb.AcquringModule.models.*;
import com.ekenya.rnd.backend.fskcb.AcquringModule.models.reqs.*;
import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.models.reqs.AssetByIdRequest;
import com.ekenya.rnd.backend.fskcb.CorporateBankingModule.models.reqs.CBAssignLeadRequest;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.models.reqs.DSRTAssignTargetRequest;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.models.reqs.TeamTAssignTargetRequest;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.models.reqs.VoomaTargetByIdRequest;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

public interface IAcquiringPortalService {

    //Leads
    //assign lead to dsrName
    boolean assignLeadToDsr(CBAssignLeadRequest model);




    List<ObjectNode> loadTargets();
    boolean addNewTarget(AcquiringAddTargetRequest acquiringAddTargetRequest);

    //Assets
    boolean addAsset(String assetDetails, MultipartFile[] assetFiles);

      //get all assets
      List<ObjectNode> getAllAssets();


      boolean deleteAssetById(Long id);

    //Visits
    List<ObjectNode> loadQuestionnaires();

    List<ObjectNode> loadDSRsInTarget(AcquiringDSRsInTargetRequest model);

    boolean scheduleCustomerVisit(CustomerVisitsRequest customerVisitsRequest);

    //update scheduled customer visit
    boolean reScheduleCustomerVisit(CustomerVisitsRequest customerVisitsRequest, Long id);

    List<ObjectNode> loadCustomerVisits();

    boolean addNewQuestionnaire(AcquiringAddQuestionnaireRequest acquiringAddQuestionnaireRequest);

    List<?> getCustomerVisitQuestionnaireResponses(Long visitId, Long questionnaireId);

    List<ObjectNode> getAllLeads();

    boolean approveMerchantOnboarding(AcquiringApproveMerchant model);

    List<ObjectNode> loadAllOnboardedMerchants();

    List<?> getOnboardingSummary(AcquringSummaryRequest filters);

    ObjectNode getMerchantById(AcquiringMerchantDetailsRequest acquiringMerchantDetailsRequest);

    List<ObjectNode> getTargetsSummary(AcquringSummaryRequest filters);

    List<?> getLeadsSummary(AcquringSummaryRequest filters);

    List<?> getAssetsSummary(AcquringSummaryRequest filters);

    boolean assignTargetToDSR(DSRTAssignTargetRequest model);

    boolean assignTargetToTeam(TeamTAssignTargetRequest model);

    Object getTargetById(VoomaTargetByIdRequest model);

    List<ObjectNode> loadAllApprovedMerchants();

    boolean rejectMerchantOnboarding(AcquiringApproveMerchant model);

    Object getAssetById(AssetByIdRequest model);

    boolean assignMerchantToDSR(AssignMerchant model);

    List<ObjectNode> findBySerialNumber(AssetInvetoryRequest model);

    List<ObjectNode> salesPersonTarget(AcquiringDSRsInTargetRequest model);

    List<ObjectNode> getDsrMerchant(DSRMerchantRequest model);

}
