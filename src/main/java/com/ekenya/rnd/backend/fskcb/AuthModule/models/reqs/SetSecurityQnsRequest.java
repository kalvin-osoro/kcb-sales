package com.ekenya.rnd.backend.fskcb.AuthModule.models.reqs;

import lombok.Data;

import java.util.List;

@Data
public class SetSecurityQnsRequest {

    //private String staffNo;

    private List<SecQnAnswerReq> answers;

}
