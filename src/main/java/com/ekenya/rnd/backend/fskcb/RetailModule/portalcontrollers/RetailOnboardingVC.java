package com.ekenya.rnd.backend.fskcb.RetailModule.portalcontrollers;

import com.ekenya.rnd.backend.fskcb.service.CustomerDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@RestController("/api/retail")
public class RetailOnboardingVC {
    @Autowired
    CustomerDetailsService customerDetailsService;
    @PostMapping("/create-retail-customer-account")
    public ResponseEntity<?> createCustomerAccount(@RequestParam("customerdata") String customerdata,
                                                   @RequestPart("frontIdCapture") MultipartFile frontIdCapture,
                                                   @RequestPart("backIdCapture") MultipartFile backIdCapture,
                                                   @RequestPart("passportPhotoCapture") MultipartFile passportPhotoCapture,
                                                   HttpServletRequest httpServletRequest){
        return customerDetailsService.addCustomer(frontIdCapture,backIdCapture, passportPhotoCapture, customerdata, httpServletRequest);
    }



}
