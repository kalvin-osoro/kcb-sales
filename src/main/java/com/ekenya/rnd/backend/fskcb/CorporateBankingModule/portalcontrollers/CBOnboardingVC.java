package com.ekenya.rnd.backend.fskcb.CorporateBankingModule.portalcontrollers;

import com.ekenya.rnd.backend.fskcb.service.CustomerAppointmentService;
import com.ekenya.rnd.backend.fskcb.service.CustomerDetailsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@RestController("corporate-banking-onboarding")
public class CBOnboardingVC {

    private final CustomerDetailsService customerDetailsService;

    private  final CustomerAppointmentService customerAppointmentService;

    public CBOnboardingVC(CustomerDetailsService customerDetailsService, CustomerAppointmentService customerAppointmentService) {
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
