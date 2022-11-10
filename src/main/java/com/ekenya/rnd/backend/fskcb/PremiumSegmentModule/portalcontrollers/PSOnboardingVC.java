package com.ekenya.rnd.backend.fskcb.PremiumSegmentModule.portalcontrollers;

import com.ekenya.rnd.backend.fskcb.service.CustomerAppointmentService;
import com.ekenya.rnd.backend.fskcb.service.CustomerDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@RestController("/premium-customer-onboarding")
public class PSOnboardingVC {
    @Autowired
    CustomerDetailsService customerDetailsService;
    @Autowired
    CustomerAppointmentService customerAppointmentService;
    @PostMapping("/create-customer-account")
    public ResponseEntity<?> createCustomerAccount(@RequestParam("customerdata") String customerdata,
                                                   @RequestParam("frontIdCapture") MultipartFile frontIdCapture,
                                                   @RequestParam("backIdCapture") MultipartFile backIdCapture,
                                                   @RequestParam("passportPhotoCapture") MultipartFile passportPhotoCapture,
                                                   HttpServletRequest httpServletRequest){
        return customerDetailsService.addCustomer(frontIdCapture,backIdCapture, passportPhotoCapture, customerdata, httpServletRequest);
    }



}
