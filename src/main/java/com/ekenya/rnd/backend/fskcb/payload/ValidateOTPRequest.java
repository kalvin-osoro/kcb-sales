package com.ekenya.rnd.backend.fskcb.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValidateOTPRequest {
    private String phoneNumber;
    private String staffId;
    private int otpNumber;
}
