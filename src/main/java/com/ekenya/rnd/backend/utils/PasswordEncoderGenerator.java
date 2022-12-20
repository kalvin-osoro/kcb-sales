package com.ekenya.rnd.backend.utils;

import com.ekenya.rnd.backend.fskcb.DSRModule.datasource.entities.DSRAccountEntity;
import com.ekenya.rnd.backend.fskcb.DSRModule.datasource.repositories.IDSRAccountsRepository;
import com.google.gson.Gson;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

public class PasswordEncoderGenerator {
    private static final String url= "jdbc:mysql://localhost:3306/ekenya_rnd_backend";
    private static final  Integer length = 10;
    @Autowired
  private static   IDSRAccountsRepository dsrAccountsRepository;


    public static void main(String[] args) {
        String password = "admin";
        String encodedPassword = new BCryptPasswordEncoder().encode(password);
        System.out.println(encodedPassword);
        System.out.println(url+length);
        String customer= "\"[{\\\"accountid\\\":\\\"fba0b3d9-e2aa-432a-aa86-000000811101\\\",\\\"name\\\":\\\"BEATRICE ADHIAMBO OKECH\\\",\\\"accno\\\":\\\"111554\\\",\\\"email\\\":\\\"\\\",\\\"telephone\\\":\\\"\\\",\\\"kcbidno\\\":\\\"13634828\\\"}]\"";
        String customer1 = customer.replace(String.valueOf(92),"");
        System.out.println(customer1);
    }



}
