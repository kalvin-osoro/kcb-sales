package com.ekenya.rnd.backend.fskcb.AuthModule.services;

import com.ekenya.rnd.backend.fskcb.AuthModule.models.reqs.*;
import com.ekenya.rnd.backend.fskcb.AuthModule.models.resp.AccountLookupState;
import com.ekenya.rnd.backend.fskcb.AuthModule.models.resp.LoginResponse;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.List;

public interface IAuthService {

    LoginResponse attemptAdminLogin(PortalLoginRequest model);
    LoginResponse attemptChannelLogin(ChannelLoginRequest model);

    boolean attemptChangePIN(ChangePINRequest model);

    AccountLookupState accountExists(LookupRequest model);

    String sendVerificationCode(SendVerificationCodeRequest model);

    boolean validateVerificationCode(ValidateVerificationCodeRequest model);
    List<ObjectNode> loadSecurityQuestions();

    List<ObjectNode> loadUserSecurityQuestions(String staffNo);

    boolean setSecurityQuestions(SetSecurityQnsRequest model);

    boolean updateSecurityQuestions(UpdateSecurityQnsRequest model);
    boolean validateSecurityQuestions(ValidateSecurityQnsRequest model);

    boolean attemptCreatePIN(CreatePINRequest model);

    boolean attemptChangePassword(ChangePasswordRequest model);

    boolean attemptRecoverPassword(ForgotPasswordRequest model);

    boolean resetDSRPIN(ResetDSRPINRequest model);

    boolean changeDSRPhoneNo(ChangeDSRPhoneNoRequest model);

    List<ObjectNode> getLoginLogs();
}
