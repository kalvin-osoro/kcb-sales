package com.ekenya.rnd.backend.fskcb.DFSVoomaModule.services;

import com.ekenya.rnd.backend.fskcb.entity.*;
import com.ekenya.rnd.backend.fskcb.payload.*;
import com.ekenya.rnd.backend.fskcb.files.IFileStorageService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.logging.Logger;

@Slf4j
@Service
public class VoomaChannelService implements IVoomaChannelService {

    @Autowired
    IFileStorageService IFileStorageService;
    @Autowired
    ModelMapper modelMapper;
    private final Logger logger = Logger.getLogger(VoomaChannelService.class.getName());
    @Override
    public ResponseEntity<?> addMerchant(MultipartFile frontID,
                                         MultipartFile backID,
                                         MultipartFile kraPinCertificate,
                                         MultipartFile certificateOFGoodConduct,
                                         MultipartFile businessLicense,
                                         MultipartFile fieldApplicationForm,
                                         String merchDetails,
                                         MultipartFile shopPhoto,
                                         MultipartFile customerPhoto,
                                         MultipartFile companyRegistrationDoc,
                                         MultipartFile signatureDoc, MultipartFile businessPermitDoc,
                                         HttpServletRequest httpServletRequest) {

        return ResponseEntity.ok("");
    }


    @Override
    public ResponseEntity<?> findMerchantByAccountNumber(String accountId) {

        return ResponseEntity.ok("");
    }

    @Override
    public ResponseEntity<?> findMerchantGeoMapDetails(String accountId) {
        //TODO Auto-generated method stub


        return ResponseEntity.ok("");
    }

    @Override
    public ResponseEntity<?> getAllMerchantDetailByAgentId(long agentId) {

        return ResponseEntity.ok("");
    }

    @Override
    public ResponseEntity<?> getAllAgentsByAgentId(long agentId) {


        return ResponseEntity.ok("");
    }

    @Override
    public ResponseEntity<?> getAllMerchantAccountTypes() {
        // TODO
        return null;
    }


    @Override
    public ResponseEntity<?> createLiquidationType(LiquidationTypeDto liquidationTypeDto) {

        return ResponseEntity.ok("");
    }

    @Override
    public LiquidationResponse getAllLiquidationType(int pageNo, int pageSize, String sortBy, String sortDir) {
        logger.info("Getting all liquidation types");
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = org.springframework.data.domain.PageRequest.of(pageNo, pageSize, sort);
        //create a pageable instance

        return null;
    }

    @Override
    public ResponseEntity<?> getLiquidationTypeById(Long id) {
        log.info("Getting liquidation type by id = {}", id);

        return ResponseEntity.ok("");
    }

    @Override
    public ResponseEntity<?> updateLiquidationType(LiquidationTypeDto liquidationTypeDto ) {

        return ResponseEntity.ok("");
    }
//update liquidation type with id = {}




    @Override
    public ResponseEntity<?> deleteLiquidationTypeById(Long id) {
        log.info("Deleting liquidation type by id = {}", id);


        return ResponseEntity.ok("");

    }
    //convert entity to dto
    private LiquidationTypeDto mapToDto(LiquidationType liquidationType) {

        return modelMapper.map(liquidationType, LiquidationTypeDto .class);

    }
    //convert dto to entity
    private LiquidationType mapToEntity(LiquidationTypeDto liquidationTypeDto) {

        return modelMapper.map(liquidationTypeDto, LiquidationType.class);

    }


////

    @Override
    public ResponseEntity<?> createBusinessType(BusinessTypeDto businessTypeDto) {
        log.info("Creating business type");
        BusinessType businessType = modelMapper.map(businessTypeDto, BusinessType.class);


        return ResponseEntity.ok(businessType);

    }

    @Override
    public ResponseEntity<?> getAllBusinessTypes(int pageNo, int pageSize, String sortBy, String sortDir) {
        log.info("Getting all business types");
        Sort sort=sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
        Pageable pageable=org.springframework.data.domain.PageRequest.of(pageNo, pageSize, sort);
        //create a pageable instance

        return ResponseEntity.ok("");

    }

    @Override
    public BusinessTypeDto getBusinessTypesById(Long id) {
        log.info("Getting business type by id {}", id);

        return null;
    }

    @Override
    public ResponseEntity<?> updateBusinessTypes(BusinessTypeDto businessTypeDto, Long id) {
        log.info("Updating business type by id {}", id);

        return ResponseEntity.ok("");
    }

    @Override
    public void deleteBusinessTypeById(Long id) {
        log.info("Deleting business type by id {}", id);



    }
    private BusinessTypeDto mapToDto(BusinessType businessType) {

        return modelMapper.map(businessType, BusinessTypeDto .class);

    }
    //convert dto to entity
    private BusinessType mapToEntity(BusinessTypeDto businessTypeDto) {

        return modelMapper.map(businessTypeDto, BusinessType.class);

    }
}
