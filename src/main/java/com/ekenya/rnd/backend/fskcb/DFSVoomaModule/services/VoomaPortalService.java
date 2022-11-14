package com.ekenya.rnd.backend.fskcb.DFSVoomaModule.services;

import com.ekenya.rnd.backend.fskcb.payload.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Service
public class VoomaPortalService implements IVoomaPortalService {

    @Override
    public ResponseEntity<?> addMerchant(MultipartFile frontID, MultipartFile backID, MultipartFile kraPinCertificate, MultipartFile certificateOFGoodConduct, MultipartFile businessLicense, MultipartFile fieldApplicationForm, String merchDetails, MultipartFile shopPhoto, MultipartFile customerPhoto, MultipartFile companyRegistrationDoc, MultipartFile signatureDoc, MultipartFile businessPermitDoc, HttpServletRequest httpServletRequest) {
        return null;
    }

    @Override
    public ResponseEntity<?> findMerchantByAccountNumber(String accountId) {
        return null;
    }

    @Override
    public ResponseEntity<?> findMerchantGeoMapDetails(String accountId) {
        return null;
    }

    @Override
    public ResponseEntity<?> getAllMerchantDetailByAgentId(long agentId) {
        return null;
    }

    @Override
    public ResponseEntity<?> getAllAgentsByAgentId(long agentId) {
        return null;
    }

    @Override
    public ResponseEntity<?> getAllMerchantAccountTypes() {
        return null;
    }

    @Override
    public ResponseEntity<?> createBusinessType(BusinessTypeDto businessTypeDto) {
        return null;
    }

    @Override
    public ResponseEntity<?> getAllBusinessTypes(int pageNo, int pageSize, String sortBy, String sortDir) {
        return null;
    }

    @Override
    public BusinessTypeDto getBusinessTypesById(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<?> updateBusinessTypes(BusinessTypeDto businessTypeDto, Long id) {
        return null;
    }

    @Override
    public void deleteBusinessTypeById(Long id) {

    }

    @Override
    public ResponseEntity<?> createLiquidationType(LiquidationTypeDto liquidationTypeDto) {
        return null;
    }

    @Override
    public LiquidationResponse getAllLiquidationType(int pageNo, int pageSize, String sortBy, String sortDir) {
        return null;
    }

    @Override
    public ResponseEntity<?> getLiquidationTypeById(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<?> updateLiquidationType(LiquidationTypeDto liquidationTypeDto) {
        return null;
    }

    @Override
    public ResponseEntity<?> deleteLiquidationTypeById(Long id) {
        return null;
    }
}
