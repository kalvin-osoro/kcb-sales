package com.ekenya.rnd.backend.fskcb.AuthModule.models.reqs;

import lombok.Data;

@Data
public class ChangePasswordRequest {
    private String currentPassword;
    private String newPassword;
}
