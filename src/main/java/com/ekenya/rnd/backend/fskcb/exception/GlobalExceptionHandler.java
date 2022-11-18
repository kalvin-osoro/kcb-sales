package com.ekenya.rnd.backend.fskcb.exception;

import com.ekenya.rnd.backend.fskcb.UserManagement.payload.ErrorDetails;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler{
    @Autowired
    ObjectMapper objectMapper;
    //handle specific exception
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetails> resourceNotFoundException(ResourceNotFoundException exception, WebRequest webRequest) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(),webRequest.getDescription(false));

        ObjectNode node = objectMapper.createObjectNode();
        node.put("status",0);
        node.put("message","Request Failed");
        node.put("data",objectMapper.createObjectNode());
        node.putPOJO("errors",errorDetails);

        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(FieldPortalApiException.class)
    public ResponseEntity<ErrorDetails> fieldPortalApiException(FieldPortalApiException exception, WebRequest webRequest) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(),webRequest.getDescription(false));

        ObjectNode node = objectMapper.createObjectNode();
        node.put("status",0);
        node.put("message","Request Failed");
        node.put("data",objectMapper.createObjectNode());
        node.putPOJO("errors",errorDetails);

        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }
    //handle global exception
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> globalExceptionHandler(Exception exception, WebRequest webRequest) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(),webRequest.getDescription(false));

        ObjectNode node = objectMapper.createObjectNode();
        node.put("status",0);
        node.put("message","Request Failed");
        node.put("data",objectMapper.createObjectNode());
        node.putPOJO("errors",errorDetails);

        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    //customize validation exception
//    @Override
//    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
//                                                                  HttpHeaders headers,
//                                                                  HttpStatus status,
//                                                                  WebRequest request) {
//        Map<String,String> errors=new HashMap<>();
//        ex.getBindingResult().getAllErrors().forEach(error->{
//            String fieldName=((FieldError)error).getField();
//            String errorMessage=error.getDefaultMessage();
//            errors.put(fieldName,errorMessage);
//        });
//        return new ResponseEntity<>(errors,HttpStatus.BAD_REQUEST);
//    }

//    @Override
//    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
//                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
//        ErrorDetails errorDetails = new ErrorDetails(new Date(), "Validation Failed",
//                ex.getBindingResult().toString());
//        return new ResponseEntity(errorDetails, HttpStatus.BAD_REQUEST);
//    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,HttpHeaders headers, HttpStatus status, WebRequest request) {

        //
        List<String> errors = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(x -> x.getDefaultMessage())
                .collect(Collectors.toList());

        ObjectNode node = objectMapper.createObjectNode();
        node.put("status",0);
        node.put("message","Request Params Validation Failed");
        node.put("data",objectMapper.createObjectNode());
        node.putPOJO("errors",errors);

        //
        return new ResponseEntity<>(node, headers, status);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> handleMessageNotReadableExceptions(final Exception ex, final WebRequest request) {
        System.out.println("MessageNotReadableException,,");
        final List<String> details = new ArrayList<>();
        details.add(ex.getMessage());
//
        ObjectNode node = objectMapper.createObjectNode();
        node.put("status",0);
        node.put("message","Request Parser Failed");
        node.put("data",objectMapper.createObjectNode());
        node.putPOJO("errors",details);
        return new ResponseEntity(node, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    protected ResponseEntity<Object> handleMethodArgumentNotValid(final Exception ex, final WebRequest request) {
        System.out.println("Validation Error Method getting executed,,");
        final List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());
        //
        ObjectNode node = objectMapper.createObjectNode();
        node.put("status",0);
        node.put("message","Request Params Validation Failed");
        node.put("data",objectMapper.createObjectNode());
        node.putPOJO("errors",details);
        //
        return new ResponseEntity("Validation Error", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    public void handleConflict(final Exception ex, HttpServletResponse response) throws IOException {
        //response.sendError(HttpStatus.BAD_REQUEST.ordinal());
        log.error("Authentication Failed for User", ex);
        ObjectNode node = objectMapper.createObjectNode();
        node.put("status",0);
        node.put("message","Authentication Failed");
        node.put("data",objectMapper.createObjectNode());
        //
        response.setContentType("application/json");
        objectMapper.writeValue(response.getOutputStream(), node);
    }
}
