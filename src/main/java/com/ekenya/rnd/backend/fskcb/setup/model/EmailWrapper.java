package com.ekenya.rnd.backend.fskcb.setup.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmailWrapper {
    private String businessUnit;
    private String profileCode;
    private String email;
}
