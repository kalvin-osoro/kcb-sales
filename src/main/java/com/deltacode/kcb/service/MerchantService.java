package com.deltacode.kcb.service;


import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

public interface MerchantService {

        ResponseEntity<?> addMerchant(MultipartFile frontID,
                                      MultipartFile backID,
                                      MultipartFile kraPinCertificate,
                                      MultipartFile certificateOFGoodConduct,
                                      MultipartFile businessLicense,
                                      MultipartFile fieldApplicationForm,
                                      String merchDetails, MultipartFile shopPhoto,
                                      MultipartFile customerPhoto,

                                      MultipartFile companyRegistrationDoc,
                                      MultipartFile signatureDoc,
                                      MultipartFile businessPermitDoc,
                                      HttpServletRequest httpServletRequest);

        ResponseEntity<?> findMerchantByAccountNumber(String accountId);
        ResponseEntity<?> findMerchantGeoMapDetails(String accountId);
        ResponseEntity<?> getAllMerchantDetailByAgentId(long agentId);
        ResponseEntity<?> getAllAgentsByAgentId(long agentId);
//        ResponseEntity<?> getCommissionData(CommissionsRequest commissionsRequest);
        ResponseEntity<?> getAllMerchantAccountTypes();
}
