package com.ekenya.rnd.backend.fskcb.AuthModule.services;

import com.ekenya.rnd.backend.fskcb.AuthModule.models.reqs.*;
import com.ekenya.rnd.backend.fskcb.AuthModule.models.resp.LoginResponse;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.List;

public interface IAuthService {

    LoginResponse attemptLogin(LoginRequest model);

    boolean attemptChangePIN(ChangePINRequest model);

    int accountExists(LookupRequest model);

    boolean sendVerificationCode(SendVerificationCodeRequest model);

    boolean validateVerificationCode(ValidateVerificationCodeRequest model);
    List<ObjectNode> loadSecurityQuestions();

    boolean setSecurityQuestions(SetSecurityQnsRequest model);

    boolean validateSecurityQuestions(ValidateSecurityQnsRequest model);

    boolean attemptCreatePIN(CreatePINRequest model);

    boolean attemptChangePassword(ChangePasswordRequest model);

    boolean resetDSRPIN(ResetDSRPINRequest model);
}
