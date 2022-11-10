package com.deltacode.kcb.service;

import com.deltacode.kcb.payload.TargetDto;
import com.deltacode.kcb.payload.TargetResponse;
import org.springframework.http.ResponseEntity;

public interface TargetService {
    ResponseEntity<?> getAllTarget( );
}
