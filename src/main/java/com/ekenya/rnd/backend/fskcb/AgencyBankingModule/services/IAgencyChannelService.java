package com.ekenya.rnd.backend.fskcb.AgencyBankingModule.services;

import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.models.reqs.*;
import com.ekenya.rnd.backend.fskcb.Calender.model.DSRAgent;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.models.reqs.DSRSummaryRequest;
import com.ekenya.rnd.backend.fskcb.TreasuryModule.models.reqs.TreasuryGetDSRLeads;
import com.ekenya.rnd.backend.fskcb.TreasuryModule.models.reqs.TreasuryUpdateLeadRequest;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

public interface IAgencyChannelService {

    boolean assignAsset(AgencyAssignAssetRequest model);

    List <ObjectNode>getAllAgentAssets(AgencyAgentAssetRequest model);

    boolean recollectAsset(AssetRecollectRequest model);

    boolean createLead(AgencyAddLeadRequest request);


    ArrayList<ObjectNode> getTargetsSummary();


    Object onboardNewCustomer(String agentDetails, MultipartFile frontID, MultipartFile backID,  MultipartFile certificateOFGoodConduct,  MultipartFile shopPhoto, MultipartFile financialStatement, MultipartFile cv, MultipartFile customerPhoto,    MultipartFile crbReport);

    List<ObjectNode> getAllOnboardingsByDsr(AgencyAllDSROnboardingsRequest model);

    boolean createCustomerVisit(String visitDetails, MultipartFile premiseInsidePhoto, MultipartFile premiseOutsidePhoto, MultipartFile cashRegisterPhoto,MultipartFile tariffPhoto);

    List<ObjectNode> loadDSRLead(TreasuryGetDSRLeads model);

    List<ObjectNode> loadAssignedDSRLead(TreasuryGetDSRLeads model);

    boolean attemptUpdateLead(TreasuryUpdateLeadRequest model);

    ArrayNode getDSRSummary(DSRSummaryRequest model);

    Object getAssetById(AssetByIdRequest model);

    Object searchAgent(AgencySearchRequest model);

    List<ObjectNode> getDSRAgent(DSRAgent model);
}
