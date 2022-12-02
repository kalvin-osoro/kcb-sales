package com.ekenya.rnd.backend.fskcb.AcquringModule.services;

import com.ekenya.rnd.backend.fskcb.AcquringModule.models.AcquiringCustomerVisitsRequest;
import com.ekenya.rnd.backend.fskcb.AcquringModule.models.AcquringSummaryRequest;
import com.ekenya.rnd.backend.fskcb.AcquringModule.models.reqs.AcquiringAddLeadRequest;
import com.ekenya.rnd.backend.fskcb.AcquringModule.models.reqs.AcquiringNearbyCustomersRequest;
import com.ekenya.rnd.backend.fskcb.AcquringModule.models.reqs.AcquiringOnboardRequest;
import com.ekenya.rnd.backend.fskcb.AcquringModule.models.reqs.SearchKeyWordRequest;
import com.ekenya.rnd.backend.fskcb.AcquringModule.models.resp.AcquiringCustomerLookupResponse;
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

    Object updateLead(AcquiringAddLeadRequest model);

    List<ObjectNode> searchCustomers(SearchKeyWordRequest model);

    List<ObjectNode> getNearbyCustomers(AcquiringNearbyCustomersRequest model);

    List<ObjectNode> getTargetsSummary();

    boolean createCustomerVisit(AcquiringCustomerVisitsRequest model);

    boolean updateCustomerVisit(AcquiringCustomerVisitsRequest model);

    List<?> getAllCustomerVisitsByDSR(int dsrId);

    boolean assignAssetToMerchant(Long assetId, Long agentId);

    List<ObjectNode> getAllAgentsAssets(Long agentId);

    Object onboardNewMerchant(String merchDetails , MultipartFile[] signatureDoc);
}
