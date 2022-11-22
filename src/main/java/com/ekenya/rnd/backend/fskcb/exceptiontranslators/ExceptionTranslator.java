package com.ekenya.rnd.backend.fskcb.exceptiontranslators;

import com.ekenya.rnd.backend.responses.RequestValidationError;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ExceptionTranslator {

    @Autowired
    ObjectMapper objectMapper;
    /**
     * Exception handler for validation errors caused by method
     * parameters
     * @RequesParam,
     * @PathVariable,
     * @RequestHeader annotated with javax.validation constraints.
     */
    @ExceptionHandler
    //@ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<?> handleConstraintViolationException(ConstraintViolationException exception)    {

        List<RequestValidationError> apiErrors = new ArrayList<>();
        //
        for (ConstraintViolation<?> violation :  exception.getConstraintViolations()) {
            String value = (violation.getInvalidValue() == null ? null : violation.getInvalidValue().toString());
            apiErrors.add(new  RequestValidationError(violation.getPropertyPath().toString(), value, violation.getMessage()));
        }
        //
        ObjectNode node = objectMapper.createObjectNode();
        node.put("status",0);
        node.put("message","Request Validation Failed.");
        node.put("data",objectMapper.createObjectNode());
        node.putPOJO("errors",apiErrors);
        //
        return ResponseEntity.badRequest().body(node);
    }
}
