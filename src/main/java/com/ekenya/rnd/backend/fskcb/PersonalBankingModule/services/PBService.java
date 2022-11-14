package com.ekenya.rnd.backend.fskcb.PersonalBankingModule.services;

import com.ekenya.rnd.backend.fskcb.payload.PersonalAccountTypeRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PBService  implements IPBService{
    @Override
    public ResponseEntity<?> getAllAccountTypes() {
        return null;
    }

    @Override
    public ResponseEntity<?> createPersonalAccountType(PersonalAccountTypeRequest personalAccountTypeRequest) {
        return null;
    }

    @Override
    public ResponseEntity<?> editPersonalAccountType(PersonalAccountTypeRequest personalAccountTypeRequest) {
        return null;
    }

    @Override
    public ResponseEntity<?> deletePersonalAccountType(long id) {
        return null;
    }
}
