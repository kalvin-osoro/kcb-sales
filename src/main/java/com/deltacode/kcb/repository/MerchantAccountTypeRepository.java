package com.deltacode.kcb.repository;

import com.deltacode.kcb.entity.MerchantAccountType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MerchantAccountTypeRepository extends JpaRepository<MerchantAccountType,Long> {

}
