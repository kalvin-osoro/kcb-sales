package com.deltacode.kcb.service;

import com.deltacode.kcb.payload.PersonalAccountTypeRequest;
import org.springframework.http.ResponseEntity;

public interface PersonalAccountTypeService {
    ResponseEntity<?> getAllAccountTypes();
    ResponseEntity<?> createPersonalAccountType( PersonalAccountTypeRequest personalAccountTypeRequest);
    ResponseEntity<?> editPersonalAccountType( PersonalAccountTypeRequest personalAccountTypeRequest);
    ResponseEntity<?> deletePersonalAccountType( long id);
}
