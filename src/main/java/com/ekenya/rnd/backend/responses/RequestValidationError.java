package com.ekenya.rnd.backend.responses;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestValidationError {

    @JsonIgnore
    private int code;
    private String field;
    private String value;
    private String message;

    public RequestValidationError(String message) {
        this.message = message;
    }

    public RequestValidationError(String field, String value, String message) {
        this.field = field;
        this.value = value;
        this.message = message;
    }
}
