package com.deltacode.kcb.payload;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuestionTypeRequest {
    private String name;
    private String expectedResponse;
    private Long createdBy;
}
