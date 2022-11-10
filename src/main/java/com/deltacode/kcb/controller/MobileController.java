package com.deltacode.kcb.controller;

import com.deltacode.kcb.payload.ValidateOTPRequest;
import com.deltacode.kcb.service.SmsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(path = "/sms")
public class MobileController {
    private final SmsService smsService;

    public MobileController(SmsService smsService) {
        this.smsService = smsService;
    }

    //send otp to mobile parameter phoneNo and staffId
    @RequestMapping(value = "/send-otp/{phoneNumber}/{staffId}", method = RequestMethod.GET)
    ResponseEntity<?> sendOTP(@PathVariable String phoneNumber, @PathVariable String staffId) {
        {

            return smsService.sendOTP(phoneNumber, staffId);
        }
    }
    @RequestMapping(value = "/otp/validate-otp", method = RequestMethod.POST)
    public ResponseEntity<?> validateOTP(@RequestBody ValidateOTPRequest validateOTPRequest) {
        return smsService.validateOtp(validateOTPRequest);
    }
}
