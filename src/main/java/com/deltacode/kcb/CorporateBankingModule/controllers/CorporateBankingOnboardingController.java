package com.deltacode.kcb.CorporateBankingModule.controllers;

import com.deltacode.kcb.service.CustomerAppointmentService;
import com.deltacode.kcb.service.CustomerDetailsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@RestController("corporate-banking-onboarding")
public class CorporateBankingOnboardingController {

    private final CustomerDetailsService customerDetailsService;

    private  final CustomerAppointmentService customerAppointmentService;

    public CorporateBankingOnboardingController(CustomerDetailsService customerDetailsService, CustomerAppointmentService customerAppointmentService) {
        this.customerDetailsService = customerDetailsService;
        this.customerAppointmentService = customerAppointmentService;
    }

    @PostMapping("/create-corporate-banking-customer-account")
    public ResponseEntity<?> createCustomerAccount(@RequestParam("customerdata") String customerdata,
                                                   @RequestParam("frontIdCapture") MultipartFile frontIdCapture,
                                                   @RequestParam("backIdCapture") MultipartFile backIdCapture,
                                                   @RequestParam("passportPhotoCapture") MultipartFile passportPhotoCapture,
                                                   HttpServletRequest httpServletRequest){
        return customerDetailsService.addCustomer(frontIdCapture,backIdCapture, passportPhotoCapture, customerdata, httpServletRequest);
    }
}
