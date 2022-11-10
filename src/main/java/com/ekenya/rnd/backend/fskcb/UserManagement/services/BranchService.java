package com.ekenya.rnd.backend.fskcb.UserManagement.services;

import com.ekenya.rnd.backend.fskcb.payload.BranchDto;
import com.ekenya.rnd.backend.fskcb.payload.BranchResponse;

import java.util.List;

public interface BranchService {
    BranchDto createBranch(Long bankId, BranchDto branchDto);

    List<BranchDto> getBranchByBankId(Long bankId);

    BranchResponse getAllBranches(int pageNo, int pageSize, String sortBy, String sortDir );

    BranchDto getBranchById(Long bankId, Long branchId);

    BranchDto updateBranch(Long bankId, Long branchId, BranchDto branchDto);

    void deleteBranch(Long bankId, Long branchId);
}
