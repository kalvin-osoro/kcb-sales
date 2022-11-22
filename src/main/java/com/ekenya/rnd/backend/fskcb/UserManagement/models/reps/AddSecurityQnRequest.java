package com.ekenya.rnd.backend.fskcb.UserManagement.models.reps;

import lombok.Data;

import java.util.List;

@Data
public class AddSecurityQnRequest {

    private String title;

    private String type;

    private List<String> choices;
}
