package com.ekenya.rnd.backend.fskcb.UserManagement.models.reps;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AddSecurityQnRequest {

    private String title;

    private String type;

    private List<String> choices;
}
