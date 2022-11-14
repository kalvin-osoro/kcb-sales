package com.ekenya.rnd.backend.fskcb.service.impl;

import com.ekenya.rnd.backend.fskcb.entity.BusinessType;
import com.ekenya.rnd.backend.fskcb.exception.ResourceNotFoundException;
import com.ekenya.rnd.backend.fskcb.payload.BusinessTypeDto;
import com.ekenya.rnd.backend.fskcb.payload.BusinessTypeResponse;
import com.ekenya.rnd.backend.fskcb.repository.BusinessTypeRepository;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.services.BusinessTypeService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j

@Service
public class BusinessTypeImpl implements BusinessTypeService {
    private final BusinessTypeRepository businessTypeRepository;
    private  final ModelMapper modelMapper;

    public BusinessTypeImpl(BusinessTypeRepository businessTypeRepository, ModelMapper modelMapper) {
        this.businessTypeRepository = businessTypeRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public ResponseEntity<?> createBusinessType(BusinessTypeDto businessTypeDto) {
        log.info("Creating business type");
        BusinessType businessType = modelMapper.map(businessTypeDto, BusinessType.class);
        businessTypeRepository.save(businessType);
         modelMapper.map(businessType, BusinessTypeDto.class);
        return ResponseEntity.ok(businessType);

    }

    @Override
    public ResponseEntity<?> getAllBusinessTypes(int pageNo, int pageSize, String sortBy, String sortDir) {
        log.info("Getting all business types");
        Sort sort=sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
        Pageable pageable=org.springframework.data.domain.PageRequest.of(pageNo, pageSize, sort);
        //create a pageable instance
        Page<BusinessType> businessTypes=businessTypeRepository.findAll(pageable);
        //get content for page object
        List<BusinessType> businessTypeList=businessTypes.getContent();
        List<BusinessTypeDto> content=businessTypeList.stream().map(businessType -> modelMapper.map(businessType, BusinessTypeDto.class)).collect(Collectors.toList());
        BusinessTypeResponse businessTypeResponse=new BusinessTypeResponse();
        businessTypeResponse.setContent(content);
        businessTypeResponse.setPageNo(businessTypes.getNumber());
        businessTypeResponse.setPageSize(businessTypes.getSize());
        businessTypeResponse.setTotalElements((int) businessTypes.getTotalElements());
        businessTypeResponse.setTotalPages(businessTypes.getTotalPages());
        businessTypeResponse.setLast(businessTypes.isLast());
        return ResponseEntity.ok(businessTypeResponse);

    }

    @Override
    public BusinessTypeDto getBusinessTypesById(Long id) {
        log.info("Getting business type by id {}", id);
        BusinessType businessType = businessTypeRepository.findById(id).orElseThrow(() -> new RuntimeException("Business Type not found"));
        return modelMapper.map(businessType, BusinessTypeDto.class);
    }

    @Override
    public ResponseEntity<?> updateBusinessTypes(BusinessTypeDto businessTypeDto, Long id) {
        log.info("Updating business type by id {}", id);
        HashMap<String, Object> responseObject = new HashMap<>();
        HashMap<String, Object> responseParams = new HashMap<>();
        try {
            log.info("Updating liquidation type with id = {}", id);
            BusinessType businessType = businessTypeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("LiquidationType", "id", id));
            //convert Dto to entity
            BusinessType updatedBusinessType = mapToEntity(businessTypeDto);
            BusinessType newBusinessType = businessTypeRepository.save(updatedBusinessType);
            responseObject.put("status", "success");
            responseObject.put("message", "Business type "
                    +businessTypeDto.getBusinessTypeName()+" successfully updated");
            responseObject.put("data", responseParams);
            //convert entity to Dto
            mapToDto(newBusinessType);
            return ResponseEntity.ok(responseObject);
        } catch (ResourceNotFoundException e) {
            log.error("Error updating business type", e);
            responseObject.put("status", "error");
            responseObject.put("message", e.getMessage());
            responseParams.put("businessType", null);
            responseObject.put("params", responseParams);
            modelMapper.map(responseObject, BusinessTypeDto.class);
            return ResponseEntity.ok(responseObject);
        }
    }

    @Override
    public void deleteBusinessTypeById(Long id) {
        log.info("Deleting business type by id {}", id);
        BusinessType businessType = businessTypeRepository.findById(id).orElseThrow(() -> new RuntimeException("Business Type not found"));
        businessTypeRepository.delete(businessType);

    }
    private BusinessTypeDto mapToDto(BusinessType businessType) {

        return modelMapper.map(businessType, BusinessTypeDto .class);

    }
    //convert dto to entity
    private BusinessType mapToEntity(BusinessTypeDto businessTypeDto) {

        return modelMapper.map(businessTypeDto, BusinessType.class);

    }
}
