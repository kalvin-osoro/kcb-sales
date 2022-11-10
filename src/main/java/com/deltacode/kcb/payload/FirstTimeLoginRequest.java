package com.deltacode.kcb.payload;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class FirstTimeLoginRequest {
    private String password;
    private String phoneNumber;
    private String staffId;

}
