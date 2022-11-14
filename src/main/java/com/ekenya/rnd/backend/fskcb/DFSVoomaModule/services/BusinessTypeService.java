package com.ekenya.rnd.backend.fskcb.DFSVoomaModule.services;

import com.ekenya.rnd.backend.fskcb.payload.BusinessTypeDto;
import org.springframework.http.ResponseEntity;

public interface BusinessTypeService {
    ResponseEntity<?> createBusinessType(BusinessTypeDto businessTypeDto);
    ResponseEntity<?> getAllBusinessTypes(int pageNo, int pageSize, String sortBy, String sortDir );

    BusinessTypeDto getBusinessTypesById(Long id);
    ResponseEntity<?> updateBusinessTypes(BusinessTypeDto businessTypeDto, Long id);
    void deleteBusinessTypeById(Long id);
}
