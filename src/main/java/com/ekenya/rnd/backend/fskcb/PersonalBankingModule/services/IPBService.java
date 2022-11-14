package com.ekenya.rnd.backend.fskcb.PersonalBankingModule.services;

import com.ekenya.rnd.backend.fskcb.payload.PersonalAccountTypeRequest;
import org.springframework.http.ResponseEntity;

public interface IPBService {


    ResponseEntity<?> getAllAccountTypes();
    ResponseEntity<?> createPersonalAccountType( PersonalAccountTypeRequest personalAccountTypeRequest);
    ResponseEntity<?> editPersonalAccountType( PersonalAccountTypeRequest personalAccountTypeRequest);
    ResponseEntity<?> deletePersonalAccountType( long id);
}
