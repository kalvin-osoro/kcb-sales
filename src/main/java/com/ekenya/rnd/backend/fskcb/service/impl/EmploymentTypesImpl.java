package com.ekenya.rnd.backend.fskcb.service.impl;

import com.ekenya.rnd.backend.fskcb.entity.EmploymentType;
import com.ekenya.rnd.backend.fskcb.exception.ResourceNotFoundException;
import com.ekenya.rnd.backend.fskcb.payload.EmploymentTypeDto;
import com.ekenya.rnd.backend.fskcb.payload.EmploymentTypeResponse;
import com.ekenya.rnd.backend.fskcb.repository.EmploymentTypeRepository;
import com.ekenya.rnd.backend.fskcb.service.EmploymentTypeService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class EmploymentTypesImpl implements EmploymentTypeService {
    private  final EmploymentTypeRepository employmentTypeRepository;
    private final ModelMapper modelMapper;

    public EmploymentTypesImpl(EmploymentTypeRepository employmentTypeRepository, ModelMapper modelMapper) {
        this.employmentTypeRepository = employmentTypeRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public EmploymentTypeDto createEmploymentType(EmploymentTypeDto employmentTypeDto) {
        log.info("Creating employment type");
        EmploymentType employmentType = modelMapper.map(employmentTypeDto, EmploymentType.class);
        employmentTypeRepository.save(employmentType);
        return modelMapper.map(employmentType, EmploymentTypeDto.class);
    }

    @Override
    public EmploymentTypeResponse getAllEmploymentTypes(int pageNo, int pageSize, String sortBy, String sortDir) {
        log.info("Getting all employment types");
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        //create a pageable instance
        Page<EmploymentType> employmentTypes = employmentTypeRepository.findAll(pageable);
        //get content for page object
        List<EmploymentType> listOfEmploymentTypes = employmentTypes.getContent();
        List<EmploymentTypeDto> content = listOfEmploymentTypes.stream().map(employmentType ->mapToDto(employmentType)).collect(Collectors.toList());
        EmploymentTypeResponse employmentTypeResponse = new EmploymentTypeResponse();
        employmentTypeResponse.setContent(content);
        employmentTypeResponse.setPageNo(employmentTypes.getNumber());
        employmentTypeResponse.setPageSize(employmentTypes.getSize());
        employmentTypeResponse.setTotalElements(employmentTypes.getNumberOfElements());
        employmentTypeResponse.setTotalPages(employmentTypes.getTotalPages());
        employmentTypeResponse.setLast(employmentTypes.isLast());
        return employmentTypeResponse;
    }

    @Override
    public EmploymentTypeDto getEmploymentTypesById(Long id) {
        log.info("Getting employment type by id = {}", id);
        EmploymentType employmentType = employmentTypeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("EmploymentType", "id", id));
        return mapToDto(employmentType);
    }

    @Override
    public EmploymentTypeDto updateEmploymentTypes(EmploymentTypeDto employmentTypeDto, Long id) {
        HashMap<String, Object> responseObject = new HashMap<>();
        HashMap<String, Object> responseParams = new HashMap<>();
        try {
            log.info("Updating employment type with id: {}", id);
            EmploymentType employmentType = employmentTypeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("EmploymentType", "id", id));
            employmentType.setEmploymentTypeName(employmentTypeDto.getEmploymentTypeName());
            employmentTypeRepository.save(employmentType);
            responseObject.put("status", "success");//set status
            responseObject.put("message", "Employment type "
                    +employmentTypeDto.getEmploymentTypeName()+" successfully updated");//set message
            responseObject.put("data", responseParams);
            return mapToDto(employmentType);


        } catch (Exception e) {
            responseObject.put("status", "failed");
            responseObject.put("message", e.getMessage());
            return null;
        }

    }

    @Override
    public ResponseEntity<?> deleteEmploymentTypeById(Long id) {
       HashMap<String,Object> responseObject = new HashMap<>();
       HashMap<String,Object>responseParam= new HashMap<>();

       try {
              log.info("Deleting employment type with id: {}", id);
              EmploymentType employmentType = employmentTypeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("EmploymentType", "id", id));
              employmentTypeRepository.delete(employmentType);
              responseObject.put("status", "success");//set status
              responseObject.put("message", "Employment type "
                     +employmentType.getEmploymentTypeName()+" successfully deleted");//set message
              responseObject.put("data", responseParam);
              return ResponseEntity.ok(responseObject);
         } catch (Exception e) {
              responseObject.put("status", "failed");
              responseObject.put("message", e.getMessage());
              return ResponseEntity.ok(responseObject);
       }

    }
    //convert entity to dto
    private EmploymentTypeDto mapToDto(EmploymentType employmentType) {

        return modelMapper.map(employmentType, EmploymentTypeDto .class);

    }
    //convert dto to entity
    private EmploymentType mapToEntity(EmploymentTypeDto employmentTypeDto) {

        return modelMapper.map(employmentTypeDto, EmploymentType.class);

    }
}
