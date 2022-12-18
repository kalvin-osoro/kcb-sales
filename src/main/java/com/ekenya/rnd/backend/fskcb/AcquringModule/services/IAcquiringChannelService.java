package com.ekenya.rnd.backend.fskcb.AcquringModule.services;

import com.ekenya.rnd.backend.fskcb.AcquringModule.models.AcquiringCustomerVisitsRequest;
import com.ekenya.rnd.backend.fskcb.AcquringModule.models.AcquringSummaryRequest;
import com.ekenya.rnd.backend.fskcb.AcquringModule.models.reqs.*;
import com.ekenya.rnd.backend.fskcb.AcquringModule.models.resp.AcquiringCustomerLookupResponse;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.models.reqs.DSRSummaryRequest;
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

    List<ObjectNode> getAllLeads();

    List<ObjectNode> getAllAssignedLeads();

    Object updateLead(TreasuryUpdateLeadRequest model);

    List<ObjectNode> searchCustomers(SearchKeyWordRequest model);

    List<ObjectNode> getNearbyCustomers(AcquiringNearbyCustomersRequest model);

    List<ObjectNode> getTargetsSummary();

    boolean createCustomerVisit(AcquiringCustomerVisitsRequest model);

    boolean updateCustomerVisit(AcquiringCustomerVisitsRequest model);

    List<?> getAllCustomerVisitsByDSR(int dsrId);

    boolean assignAssetToMerchant(Long assetId, Long agentId);

    List<ObjectNode> getAllAgentsAssets(Long agentId);

    Object onboardNewMerchant(String merchDetails , MultipartFile[] signatureDoc);

    boolean createCustomerFeedback(CustomerFeedbackRequest model);

    ArrayNode getDSRSummary(DSRSummaryRequest model);
}
