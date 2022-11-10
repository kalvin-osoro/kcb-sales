package com.ekenya.rnd.backend.fskcb.payload;

import com.ekenya.rnd.backend.fskcb.entity.QuestionsType;
import com.ekenya.rnd.backend.fskcb.utils.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QuestionsResponse {
    private Long id;
    private String question;
    private String questionDescription;
    private QuestionsType questionType;
    private Status status;
    private String choices;
}
