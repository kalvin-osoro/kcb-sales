package com.deltacode.kcb.service;

import com.deltacode.kcb.payload.BusinessTypeDto;
import com.deltacode.kcb.payload.BusinessTypeResponse;
import org.springframework.http.ResponseEntity;

public interface BusinessTypeService {
    ResponseEntity<?> createBusinessType(BusinessTypeDto businessTypeDto);
    ResponseEntity<?> getAllBusinessTypes(int pageNo, int pageSize, String sortBy, String sortDir );

    BusinessTypeDto getBusinessTypesById(Long id);
    ResponseEntity<?> updateBusinessTypes(BusinessTypeDto businessTypeDto, Long id);
    void deleteBusinessTypeById(Long id);
}
