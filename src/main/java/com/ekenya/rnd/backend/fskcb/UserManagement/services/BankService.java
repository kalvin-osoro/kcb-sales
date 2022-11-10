package com.ekenya.rnd.backend.fskcb.UserManagement.services;

import com.ekenya.rnd.backend.fskcb.payload.BankDto;
import com.ekenya.rnd.backend.fskcb.payload.BankResponse;
import org.springframework.http.ResponseEntity;

public interface BankService {
    BankDto createBank(BankDto bankDto);
    BankResponse getAllBanks(int pageNo, int pageSize, String sortBy, String sortDir );
    BankDto getBankById(Long id);
    ResponseEntity<?> updateBank(BankDto bankDto, Long id);
    void deleteBankById(Long id);
}