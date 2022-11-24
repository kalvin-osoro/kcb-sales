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

//    ResponseEntity<?> addMerchant(MultipartFile frontID,
//                                  MultipartFile backID,
//                                  MultipartFile kraPinCertificate,
//                                  MultipartFile certificateOFGoodConduct,
//                                  MultipartFile businessLicense,
//                                  MultipartFile fieldApplicationForm,
//                                  String merchDetails, MultipartFile shopPhoto,
//                                  MultipartFile customerPhoto,
//
//                                  MultipartFile companyRegistrationDoc,
//                                  MultipartFile signatureDoc,
//                                  MultipartFile businessPermitDoc,
//                                  HttpServletRequest httpServletRequest);
//
//    ResponseEntity<?> findMerchantByAccountNumber(String accountId);
//    ResponseEntity<?> findMerchantGeoMapDetails(String accountId);
//    ResponseEntity<?> getAllMerchantDetailByAgentId(long agentId);
//    ResponseEntity<?> getAllAgentsByAgentId(long agentId);
//    //        ResponseEntity<?> getCommissionData(CommissionsRequest commissionsRequest);
//    ResponseEntity<?> getAllMerchantAccountTypes();
//
//
//
//    ResponseEntity<?> createBusinessType(BusinessTypeDto businessTypeDto);
//    ResponseEntity<?> getAllBusinessTypes(int pageNo, int pageSize, String sortBy, String sortDir );
//
//    BusinessTypeDto getBusinessTypesById(Long id);
//    ResponseEntity<?> updateBusinessTypes(BusinessTypeDto businessTypeDto, Long id);
//    void deleteBusinessTypeById(Long id);
//
//
//    ResponseEntity<?> createLiquidationType(LiquidationTypeDto liquidationTypeDto);
//    LiquidationResponse getAllLiquidationType(int pageNo, int pageSize, String sortBy, String sortDir );
//    ResponseEntity<?> getLiquidationTypeById(Long id);
//    ResponseEntity<?> updateLiquidationType(LiquidationTypeDto liquidationTypeDto);
//    ResponseEntity<?> deleteLiquidationTypeById(Long id);


    boolean createLead(VoomaAddLeadRequest model);

    List<ObjectNode> getAllLeadsByDsrId(VoomaAddLeadRequest model);

    ArrayList<ObjectNode> getTargetsSummary();

    Object onboardNewMerchant(String merchDetails,
                              MultipartFile frontID,
                              MultipartFile backID,
                              MultipartFile kraPinCertificate,
                              MultipartFile certificateOFGoodConduct,
                              MultipartFile businessLicense,
                              MultipartFile shopPhoto,
                              MultipartFile customerPhoto,
                              MultipartFile companyRegistrationDoc,
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

    List<ObjectNode> getAllAgentMerchantAssets(Long merchantId);

    boolean recollectAsset(VoomaCollectAssetRequest model);
}
