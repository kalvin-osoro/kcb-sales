package com.ekenya.rnd.backend.fskcb.AuthModule.services;

import com.ekenya.rnd.backend.fskcb.AuthModule.datasource.entities.AuthCodeType;

public interface ISmsService {
    boolean sendSecurityCode(String staffNo, AuthCodeType type);

}

