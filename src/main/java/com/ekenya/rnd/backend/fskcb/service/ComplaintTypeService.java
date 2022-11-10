package com.ekenya.rnd.backend.fskcb.service;
import com.ekenya.rnd.backend.fskcb.payload.ComplaintTypeDto;
import com.ekenya.rnd.backend.fskcb.payload.ComplaintTypeResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

public interface ComplaintTypeService {
    ComplaintTypeResponse getAllComplaintTypes(int pageNo, int pageSize, String sortBy, String sortDir );

    ComplaintTypeDto getComplaintTypesById(Long id);
    ComplaintTypeDto updateComplaintTypes(ComplaintTypeDto complaintTypeDto,Long id);
    void deleteComplaintTypeById(Long id);
    ResponseEntity<?> addComplain(String complainDetails, MultipartFile[] complainFiles,
                                  HttpServletRequest httpServletRequest );
}
