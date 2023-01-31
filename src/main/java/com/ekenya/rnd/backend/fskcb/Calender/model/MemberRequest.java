package com.ekenya.rnd.backend.fskcb.Calender.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MemberRequest {
    private String name;
    private String salesCode;
    private String phoneNumber;
    private String email;

}
