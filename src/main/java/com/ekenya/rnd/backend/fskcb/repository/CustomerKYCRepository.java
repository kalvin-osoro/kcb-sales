package com.ekenya.rnd.backend.fskcb.repository;

import com.ekenya.rnd.backend.fskcb.entity.CustomerDetails;
import com.ekenya.rnd.backend.fskcb.entity.CustomerKYC;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerKYCRepository extends JpaRepository<CustomerKYC,Long> {
    Optional<CustomerKYC> findCustomerKycByCustomerDetails(CustomerDetails customerDetails);
}
