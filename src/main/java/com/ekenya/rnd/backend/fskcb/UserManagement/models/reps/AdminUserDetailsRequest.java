package com.ekenya.rnd.backend.fskcb.UserManagement.models.reps;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.Optional;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AdminUserDetailsRequest {

    private Optional<Long> userId;
    private Optional<String> staffNo;
}
