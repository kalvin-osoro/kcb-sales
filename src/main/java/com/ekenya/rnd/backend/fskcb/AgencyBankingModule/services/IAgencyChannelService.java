package com.ekenya.rnd.backend.fskcb.AgencyBankingModule.services;

import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.models.reqs.*;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

public interface IAgencyChannelService {

    boolean assignAsset(AgencyAssignAssetRequest model);

    List <ObjectNode>getAllAgentAssets(Long agentId);

    boolean recollectAsset(AssetRecollectRequest model);

    boolean createLead(AgencyAddLeadRequest request);

    List<ObjectNode> getAllLeadsByDsrId(DSRLeadRequest model);

    ArrayList<ObjectNode> getTargetsSummary();


    Object onboardNewCustomer(String agentDetails, MultipartFile frontID, MultipartFile backID,  MultipartFile certificateOFGoodConduct,  MultipartFile shopPhoto, MultipartFile financialStatement, MultipartFile cv, MultipartFile customerPhoto,    MultipartFile crbReport);

    List<ObjectNode> getAllOnboardingsByDsr(AgencyAllDSROnboardingsRequest model);

    boolean createCustomerVisit(String visitDetails, MultipartFile premiseInsidePhoto, MultipartFile premiseOutsidePhoto, MultipartFile cashRegisterPhoto,MultipartFile tariffPhoto);
}
