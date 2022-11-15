package com.ekenya.rnd.backend.fskcb.AuthModule.models.reqs;

import java.util.List;

public class ValidateSecurityQnsRequest {

    private String staffNo;
    private List<SecQnAnswerModel> answers;

    private class SecQnAnswerModel{
        private int qnId;
        private String answer;

        public int getQnId() {
            return qnId;
        }

        public void setQnId(int qnId) {
            this.qnId = qnId;
        }

        public String getAnswer() {
            return answer;
        }

        public void setAnswer(String answer) {
            this.answer = answer;
        }
    }
}
