package com.deltacode.kcb.service;

import com.deltacode.kcb.entity.CustomerDetails;
import com.deltacode.kcb.payload.GetCustomerRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface CustomerDetailsService {
    List<CustomerDetails>getAllCustomerDetails();
    ResponseEntity<?>getCustomerDetails(GetCustomerRequest getCustomerRequest);
    ResponseEntity<?>addCustomer(MultipartFile frontIdCapture,
                                 MultipartFile backIdCapture,
                                 MultipartFile passportPhotoCapture,
                                 String customerData, HttpServletRequest httpServletRequest);
    ResponseEntity<?>getCustomerKYCDetails(GetCustomerRequest getCustomerRequest);
    ResponseEntity<?>getAccountDetails(GetCustomerRequest getCustomerRequest);
    ResponseEntity<?>getAllCustomerByDsrId(Long dsrId);
}
