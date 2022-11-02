package com.deltacode.kcb.repository;

import com.deltacode.kcb.entity.CustomerDetails;
import com.deltacode.kcb.entity.CustomerKYC;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerKYCRepository extends JpaRepository<CustomerKYC,Long> {
    Optional<CustomerKYC> findCustomerKycByCustomerDetails(CustomerDetails customerDetails);
}
