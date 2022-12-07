package com.ekenya.rnd.backend.fskcb.DFSVoomaModule.services;

import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.models.reqs.*;
import com.ekenya.rnd.backend.fskcb.payload.BusinessTypeDto;
import com.ekenya.rnd.backend.fskcb.payload.LiquidationResponse;
import com.ekenya.rnd.backend.fskcb.payload.LiquidationTypeDto;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public interface IVoomaChannelService {
    boolean createLead(VoomaAddLeadRequest model);

    List<ObjectNode> getAllLeadsByDsrId(VoomaAddLeadRequest model);

    ArrayList<ObjectNode> getTargetsSummary();

    Object onboardNewMerchant(String merchDetails,
                              MultipartFile frontID,
                              MultipartFile backID,
                              MultipartFile kraPinCertificate,
                              MultipartFile shopPhoto,
                              MultipartFile signatureDoc,
                              MultipartFile businessPermitDoc);

    Object onboardNewAgent(String agentDetails,
                           MultipartFile frontID,
                           MultipartFile backID,
                           MultipartFile kraPinCertificate,
                           MultipartFile businessCertificateOfRegistration,
                           MultipartFile shopPhoto,
                           MultipartFile signatureDoc,
                           MultipartFile businessPermitDoc);

    boolean assignAssetToMerchant(VoomaAssignAssetRequest model);

    boolean assignAssetToAgent(VoomaAssignAssetRequest model);

    List<ObjectNode> getAllAgentMerchantAssets(CustomerAssetsRequest model);

    boolean recollectAsset(VoomaCollectAssetRequest model);

    boolean createCustomerVisit(VoomaCustomerVisitsRequest model);

    boolean updateCustomerVisit(VoomaCustomerVisitsRequest request);

    List<ObjectNode> getAllCustomerVisitsByDSR(VoomaCustomerVisitsRequest model);


    Object getSummary(DSRSummaryRequest model);
}
