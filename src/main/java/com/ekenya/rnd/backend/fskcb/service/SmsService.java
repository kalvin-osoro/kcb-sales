package com.ekenya.rnd.backend.fskcb.service;

import com.ekenya.rnd.backend.fskcb.payload.ResetPasswordRequest;
import com.ekenya.rnd.backend.fskcb.payload.ValidateOTPRequest;
import org.springframework.http.ResponseEntity;

public interface SmsService {
    ResponseEntity<?> sendOTP(String phoneNumber,String staffId);
    ResponseEntity<?> validateOtp(ValidateOTPRequest validateOTPRequest);
    ResponseEntity<?> accountLookUp(String phoneNumber, String staffId);
    ResponseEntity<?>resetPassword(ResetPasswordRequest resetPasswordRequest);
}
