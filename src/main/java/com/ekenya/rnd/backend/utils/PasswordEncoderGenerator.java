package com.ekenya.rnd.backend.utils;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoderGenerator {
    private static final String url= "jdbc:mysql://localhost:3306/ekenya_rnd_backend";
    private static final  Integer length = 10;


    public static void main(String[] args) {
        String password = "admin";
        String encodedPassword = new BCryptPasswordEncoder().encode(password);
        System.out.println(encodedPassword);
        System.out.println(url+length);
    }

}
