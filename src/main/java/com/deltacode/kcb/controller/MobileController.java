package com.deltacode.kcb.controller;

import com.deltacode.kcb.payload.ResetPasswordRequest;
import com.deltacode.kcb.payload.ValidateOTPRequest;
import com.deltacode.kcb.service.SmsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(path = "/lookup")
public class MobileController {
    private final SmsService smsService;

    public MobileController(SmsService smsService) {
        this.smsService = smsService;
    }

    //send otp to mobile parameter phoneNo and staffId

    @RequestMapping(value = "/otp/validate-otp", method = RequestMethod.POST)
    public ResponseEntity<?> validateOTP(@RequestBody ValidateOTPRequest validateOTPRequest) {
        return smsService.validateOtp(validateOTPRequest);
    }
    @RequestMapping(value = "/account-lookup/{phoneNumber}/{staffId}", method = RequestMethod.GET)
    public ResponseEntity<?> accountLookUp(@PathVariable String phoneNumber, @PathVariable String staffId) {
        return smsService.accountLookUp(phoneNumber, staffId);
    }
    @RequestMapping(value = "/otp/send-otp/{phoneNumber}/{staffId}", method = RequestMethod.POST)
    public ResponseEntity<?> sendOTP(@PathVariable String phoneNumber, @PathVariable String staffId) {
        return smsService.sendOTP(phoneNumber, staffId);
    }
    @RequestMapping(value = "/reset-password", method = RequestMethod.POST)
    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordRequest resetPasswordRequest) {
        return smsService.resetPassword(resetPasswordRequest);
    }
}
