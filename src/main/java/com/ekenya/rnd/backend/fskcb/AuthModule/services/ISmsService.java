package com.ekenya.rnd.backend.fskcb.AuthModule.services;

import com.ekenya.rnd.backend.fskcb.AuthModule.datasource.entities.AuthCodeType;

import java.util.function.BinaryOperator;

public interface ISmsService {
    String sendSecurityCode(String staffNo, AuthCodeType type);

    boolean sendPasswordEmail(String receiverEmail,String fullName,String password,String staffNumber);

    boolean sendPasswordSMS(String phoneNo,String fullName,  String password,String staffNo);
    boolean sendDSRCreatedSMS(String phoneNo,String fullName);


    boolean sendDsrCreatedEmail(String receiverEmail,String fullName);

}

