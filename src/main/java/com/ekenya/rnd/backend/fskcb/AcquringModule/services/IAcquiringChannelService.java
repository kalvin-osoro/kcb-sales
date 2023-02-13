package com.ekenya.rnd.backend.fskcb.AcquringModule.services;

import com.ekenya.rnd.backend.fskcb.AcquringModule.models.AcquiringAssignAssetRequest;
import com.ekenya.rnd.backend.fskcb.AcquringModule.models.AcquiringCustomerVisitsRequest;
import com.ekenya.rnd.backend.fskcb.AcquringModule.models.AcquringSummaryRequest;
import com.ekenya.rnd.backend.fskcb.AcquringModule.models.reqs.*;
import com.ekenya.rnd.backend.fskcb.AcquringModule.models.resp.AcquiringCustomerLookupResponse;
import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.datasource.entities.AgencyOnboardingEntity;
import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.models.reqs.AssetByIdRequest;
import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.services.AgencySearchRequest;
import com.ekenya.rnd.backend.fskcb.Calender.model.DSRAgent;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.models.reqs.CustomerAssetsRequest;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.models.reqs.DSRSummaryRequest;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.models.reqs.VoomaAssignAssetRequest;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.models.reqs.VoomaCollectAssetRequest;
import com.ekenya.rnd.backend.fskcb.TreasuryModule.models.reqs.TreasuryGetDSRLeads;
import com.ekenya.rnd.backend.fskcb.TreasuryModule.models.reqs.TreasuryUpdateLeadRequest;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.JsonObject;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

public interface IAcquiringChannelService {

    JsonObject findCustomerByAccNo(String accNo);


    List<ObjectNode> getAllOnboardings();

    boolean createLead(AcquiringAddLeadRequest model);


    List<ObjectNode> getAllAssignedLeads(TreasuryGetDSRLeads model);

    Boolean updateLead(TreasuryUpdateLeadRequest model);

    List<ObjectNode> searchCustomers(SearchKeyWordRequest model);

    List<ObjectNode> getNearbyCustomers(AcquiringNearbyCustomersRequest model);

    List<ObjectNode> getTargetsSummary();

    boolean createCustomerVisit(AcquiringCustomerVisitsRequest model);

    boolean updateCustomerVisit(AcquiringCustomerVisitsRequest model);

    List<?> getAllCustomerVisitsByDSR(int dsrId);



    Object onboardNewMerchant(String merchDetails , MultipartFile[] signatureDoc);

    boolean createCustomerFeedback(CustomerFeedbackRequest model);

    ArrayNode getDSRSummary(DSRSummaryRequest model);

    List<ObjectNode> loadDSRLead(TreasuryGetDSRLeads model);

    boolean assignAssetToMerchant(VoomaAssignAssetRequest model);

    List<ObjectNode> getAllAgentMerchantAssets(CustomerAssetsRequest model);

    boolean recollectAsset(VoomaCollectAssetRequest model);

    Object getAssetById(AssetByIdRequest model);

    List<ObjectNode> searchAgent(AgencySearchRequest model);

    List<ObjectNode> getDSRAgent(DSRAgent model);
}
