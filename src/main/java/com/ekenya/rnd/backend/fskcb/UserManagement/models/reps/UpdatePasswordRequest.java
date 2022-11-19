package com.ekenya.rnd.backend.fskcb.UserManagement.models.reps;

import lombok.Data;

@Data
public class UpdatePasswordRequest {

    private Long userId;
    private String currentPassword;
    private String newPassword;
}
