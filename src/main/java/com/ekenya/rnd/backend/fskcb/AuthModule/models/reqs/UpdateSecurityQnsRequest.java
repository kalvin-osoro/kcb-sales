package com.ekenya.rnd.backend.fskcb.AuthModule.models.reqs;

import lombok.Data;

import java.util.List;

@Data
public class UpdateSecurityQnsRequest {

    private List<SecQnAnswerReq> answers;
}
