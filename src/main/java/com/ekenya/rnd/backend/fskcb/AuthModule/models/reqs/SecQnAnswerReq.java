package com.ekenya.rnd.backend.fskcb.AuthModule.models.reqs;

import lombok.Data;

@Data
public class SecQnAnswerReq{
    private long qnId;
    private String answer;
}
