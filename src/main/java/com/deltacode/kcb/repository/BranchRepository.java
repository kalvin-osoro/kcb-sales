package com.deltacode.kcb.repository;

import com.deltacode.kcb.entity.Branch;
import com.deltacode.kcb.entity.MerchantAccountType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository

public interface BranchRepository extends JpaRepository<Branch, Long> {
    List<Branch> findAllByBankId(Long bankId);

    Optional<Branch> findByBranchCode(String branchCode);
}

