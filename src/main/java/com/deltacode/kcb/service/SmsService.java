package com.deltacode.kcb.service;

import com.deltacode.kcb.payload.FirstTimeLoginRequest;
import com.deltacode.kcb.payload.ResetPasswordRequest;
import com.deltacode.kcb.payload.ValidateOTPRequest;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;

public interface SmsService {
    ResponseEntity<?> sendOTP(String phoneNumber,String staffId);
    ResponseEntity<?> validateOtp(ValidateOTPRequest validateOTPRequest);
    ResponseEntity<?> accountLookUp(String phoneNumber, String staffId);
    ResponseEntity<?>resetPassword(ResetPasswordRequest resetPasswordRequest);
}
