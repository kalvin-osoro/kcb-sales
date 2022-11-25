package com.ekenya.rnd.backend.utils;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoderGenerator {
    public static void main(String[] args) {
        String password = "admin";
        String encodedPassword = new BCryptPasswordEncoder().encode(password);
        System.out.println(encodedPassword);
    }
}
