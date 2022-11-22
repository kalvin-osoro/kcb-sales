package com.ekenya.rnd.backend.fskcb.AcquringModule.services;

import com.ekenya.rnd.backend.fskcb.AcquringModule.models.AcquringSummaryRequest;
import com.ekenya.rnd.backend.fskcb.AcquringModule.models.reqs.AcquiringAddLeadRequest;
import com.ekenya.rnd.backend.fskcb.AcquringModule.models.reqs.AcquiringNearbyCustomersRequest;
import com.ekenya.rnd.backend.fskcb.AcquringModule.models.resp.AcquiringCustomerLookupResponse;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.JsonObject;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

public interface IAcquiringChannelService {

    JsonObject findCustomerByAccNo(String accNo);

    Object onboardNewMerchant(String merchDetails, MultipartFile frontID, MultipartFile backID, MultipartFile kraPinCertificate, MultipartFile certificateOFGoodConduct, MultipartFile businessLicense,  MultipartFile shopPhoto, MultipartFile customerPhoto, MultipartFile companyRegistrationDoc, MultipartFile signatureDoc, MultipartFile businessPermitDoc);

    List<ObjectNode> getAllOnboardings();

    boolean createLead(AcquiringAddLeadRequest model);

    List<ObjectNode> getAllLeads();

    List<ObjectNode> getAllAssignedLeads();

    Object updateLead(AcquiringAddLeadRequest model);

    Object searchCustomers(String merchantName,String merchantPhone);

    List<ObjectNode> getNearbyCustomers(AcquiringNearbyCustomersRequest model);

    List<ObjectNode> getTargetsSummary();
}
