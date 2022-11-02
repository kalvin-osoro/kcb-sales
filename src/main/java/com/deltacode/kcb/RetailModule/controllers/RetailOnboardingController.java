package com.deltacode.kcb.RetailModule.controllers;

import com.deltacode.kcb.service.CustomerAppointmentService;
import com.deltacode.kcb.service.CustomerDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@RestController("/retail")
public class RetailOnboardingController {
    @Autowired
    private CustomerAppointmentService customerAppointmentService;
    @Autowired
    CustomerDetailsService customerDetailsService;
    @PostMapping("/create-retail-customer-account")
    public ResponseEntity<?> createCustomerAccount(@RequestParam("customerdata") String customerdata,
                                                   @RequestParam("frontIdCapture") MultipartFile frontIdCapture,
                                                   @RequestParam("backIdCapture") MultipartFile backIdCapture,
                                                   @RequestParam("passportPhotoCapture") MultipartFile passportPhotoCapture,
                                                   HttpServletRequest httpServletRequest){
        return customerDetailsService.addCustomer(frontIdCapture,backIdCapture, passportPhotoCapture, customerdata, httpServletRequest);
    }



}
