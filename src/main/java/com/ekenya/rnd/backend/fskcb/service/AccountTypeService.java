package com.ekenya.rnd.backend.fskcb.service;

import com.ekenya.rnd.backend.fskcb.payload.AccountTypeDto;
import com.ekenya.rnd.backend.fskcb.payload.AccountTypeResponse;
import org.springframework.http.ResponseEntity;

public interface AccountTypeService {
    ResponseEntity<?> createAccountType(AccountTypeDto accountTypeDto);
    AccountTypeResponse getAllAccountTypes(int pageNo, int pageSize, String sortBy, String sortDir );

    AccountTypeDto getAccountTypesById(Long id);
    ResponseEntity<?> updateAccountTypes(AccountTypeDto accountTypeDto, Long id);
    ResponseEntity<?> deleteAccountTypeById(Long id);
}
