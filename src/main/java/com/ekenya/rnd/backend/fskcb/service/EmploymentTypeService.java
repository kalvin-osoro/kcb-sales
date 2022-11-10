package com.ekenya.rnd.backend.fskcb.service;

import com.ekenya.rnd.backend.fskcb.payload.EmploymentTypeDto;
import com.ekenya.rnd.backend.fskcb.payload.EmploymentTypeResponse;
import org.springframework.http.ResponseEntity;

public interface EmploymentTypeService {
    EmploymentTypeDto createEmploymentType(EmploymentTypeDto employmentTypeDto);
    EmploymentTypeResponse getAllEmploymentTypes(int pageNo, int pageSize, String sortBy, String sortDir );

    EmploymentTypeDto getEmploymentTypesById(Long id);
    EmploymentTypeDto updateEmploymentTypes(EmploymentTypeDto employmentTypeDto,Long id);
    ResponseEntity<?> deleteEmploymentTypeById(Long id);
}
