package com.deltacode.kcb.payload;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class GetCustomerRequest {
    private Long accountNumber;

public String toJson(){
    try {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(this);
    } catch (Exception ex) {
        log.info("Error is {}",ex.getMessage());
    }
    return "{}";}
}