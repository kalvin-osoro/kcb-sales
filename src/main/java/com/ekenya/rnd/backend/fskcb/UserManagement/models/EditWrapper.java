package com.ekenya.rnd.backend.fskcb.UserManagement.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EditWrapper {
    private Long id;
    private String staffNo;
    private String fullName;
    private String email;
    private String phoneNo;
}
